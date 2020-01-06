package code;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BirdMountainRiver {
    /**
     * 1. Create height array and generate initial condition
     *      a. height array of positions
     *      b. day 0 mountain array of "^", "-" , " "
     * 2. From initial condition, define
     *      a. bank set - set of points where river is neighboured by at least one bank
     *      b. river set - set of points which are river
     *      c. test set -  nominally the bank set at the start of each day, but added to with each expansion
     *  3. for each point in the test set
     *      a. find all neighbours (as points) and check if height is lower than water level
     *      b. if height lower, add point to the river, bank and test sets
     *      c. if height higher, add this point to the bank set, and continue. at end, any positions not banked should be rmeoved
 *   *  4. when test set is empty, river rising is complete for the day
     *  5. save the new mountain array in an array, at index = day
     * @param mountain
     * @return
     */

    public static double riverHeight;

    public static int[] dryGround(char[][] mountain){
        Set<Point> riverSet = new HashSet<>();
        Set<Point> bankSet = new HashSet<>();
        Queue<Point> testSet = new PriorityQueue<>();
        Set<Integer> availableLandings = new HashSet<>();
        int[][] heightArray;
        int[][] tempHeightArray = new int[mountain.length][mountain[0].length];
        IntStream.range(0, mountain.length).
                forEach(x -> {
                    IntStream.range(0, mountain[x].length)
                            .forEach(y -> {
                                if (mountain[x][y] == '^') {
                                    tempHeightArray[x][y] = -1;
                                } else if (mountain[x][y] == '-') {
                                    riverSet.add(new Point(x, y));
                                    bankSet.add(new Point(x, y));
                                } else {
                                    tempHeightArray[x][y] = 0;
                                }
                            });
                });
        heightArray = tempHeightArray;
        for(int i =0; i<Math.max(mountain.length,mountain[0].length); i++){
            heightArray = getHeightArray(heightArray);
        }
        //Now we have the bank set and river set and initial conditions
        availableLandings.add(getAvailablePositions(heightArray)); //get starting available positions

        //main loop
        riverHeight = -0.5;
        for(int i = 0 ; i <3;i++){
            riverHeight+=1;
            testSet.addAll(bankSet);
            while(!testSet.isEmpty()){
                Point nextRiverPosition = testSet.remove();
                //3. for each point in the test set
                //     *      a. find all neighbours (as points) and check if height is lower than water level
                //     *      b. if height lower, add point to the river, bank and test sets
                //     *      c. if height higher, add this point to the bank set, and continue. at end, any positions not banked should be rmeoved

                boolean stillBank = false;
                for(Point neighbour: getNeighboursPoints(nextRiverPosition.x,nextRiverPosition.y,heightArray)){
                    if(heightArray[neighbour.x][neighbour.y] <riverHeight){
                        heightArray[neighbour.x][neighbour.y] = 0;  //now part of river
                        riverSet.add(neighbour);
                        bankSet.add(neighbour);
                        testSet.add(neighbour);
                    }
                    else if(!riverSet.contains(neighbour)){
                        stillBank = true;  //there exists an adjoining neighbour higher than current river == a bank
                    }
                }
                if(!stillBank){
                    bankSet.remove(nextRiverPosition);
                }
            }
            availableLandings.add(getAvailablePositions(heightArray));
        }
        return availableLandings.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /**
     * Returns the available positions in the height array (representing the mountains topographically)
     * @param heightArray - Array of integers from 0 to n, where n is the tallest point. Any integer greater than 0 is considerec an available landing posn
     * @return
     */
    public static int getAvailablePositions(int[][] heightArray){
        final Integer[] posns = {0};
        Arrays.stream(heightArray)
                .forEach(x -> Arrays.stream(x)
                .forEach(y -> {
                    if(y >0){
                        posns[0]++;}
                }));
        return posns[0];
    }

    public static int peakHeight(char[][] mountain) {
        int[][] copy = copyMountain(mountain);
        int m = mountain.length;
        int n = mountain[0].length;
        for(int i =0; i<Math.max(n,m); i++){
            copy = getHeightArray(copy);
        }
        return Arrays.stream(copy).map(a -> Arrays.stream(a).
                reduce(0,Math::max))
                .reduce(0,Math::max);
    }

    public static int[][] getHeightArray(int[][] copy){
        for(int x = 0; x< copy.length; x++){
            for (int y = 0; y< copy[0].length; y++){
                if(copy[x][y] != 0 ){
                    copy[x][y] = getNeighbours(x,y,copy).stream().filter(a -> a>=0).reduce(Integer.MAX_VALUE,Math::min)+1; //should always get 0 or min value
                }
            }
        }
        return copy;
    }

    public static List<Point> getNeighboursPoints(int x, int y, int[][] mountain){
        int n = mountain.length;
        int m = mountain[0].length;
        List<Point> result  =  new ArrayList<>();
        for(int a = -1;a <2;a+=2){
            if(x +a >=0 && x+ a<n){
                result.add(new Point(x+a,y));
            }
            if(y+a>=0 && y+a <m){
                result.add(new Point(x,y+a));
            }
        }
        return result;
    }

    public static List<Integer> getNeighbours(int x, int y, int[][] mountain){
        int n = mountain.length;
        int m = mountain[0].length;
        List<Integer> result  =  new ArrayList<>();
        for(int a = -1;a <2;a+=2){
            if(x +a >=0 && x+ a<n){
                result.add(mountain[x+a][y]);
            }else{
                result.add(0);
            }
            if(y+a>=0 && y+a <m){
                result.add(mountain[x][y+a]);
            }
            else{
                result.add(0);
            }
        }
        return result;
    }

    public static int[][] copyMountain(char[][] mountain){
        int[][] mountainCopy = new int[mountain.length][mountain[0].length];
        IntStream.range(0, mountain.length).
                forEach(x -> IntStream.range(0,mountain[x].length)
                        .forEach(y ->{
                            if(mountain[x][y] == '^'){mountainCopy[x][y] = -1;}
                            else{ mountainCopy[x][y] = 0;}
                        }));
        return mountainCopy;
    }
}




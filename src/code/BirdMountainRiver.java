package code;



import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.IntStream;

public class BirdMountainRiver {
    public static boolean debug = false;
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
        if(mountain.length == 0||mountain[0].length == 0){
            return new int[]{0,0,0,0};
        }
        char[][] mountainCopy = deepCopyMountain(mountain);
        Set<Point> bankSet = new HashSet<>();
        Queue<Point> testSet = new LinkedList<>();
        List<Integer> availableLandings = new LinkedList<>();
        int[][] mountainHeights;
        int[][] tempHeightArray = new int[mountainCopy.length][mountainCopy[0].length];
        IntStream.range(0, mountainCopy.length).
                forEach(x -> {
                    IntStream.range(0, mountainCopy[x].length)
                            .forEach(y -> {
                                if (mountainCopy[x][y] == '^') {
                                    tempHeightArray[x][y] = -1;
                                } else if (mountainCopy[x][y] == '-') {
                                    bankSet.add(new Point(x, y));
                                } else {
                                    tempHeightArray[x][y] = 0;
                                }
                            });
                });
        mountainHeights = tempHeightArray;
        for(int i =0; i<Math.max(mountainCopy.length,mountainCopy[0].length); i++){
            mountainHeights = getHeightArray(mountainHeights);
        }
        //Now we have the bank set and river set and initial conditions
        availableLandings.add(getAvailablePositions(mountainCopy)); //get starting available positions

        //main loop
        riverHeight = -0.5;
        for(int i = 0 ; i <3;i++){
            System.out.println("Mountain: " + mountainPrint(mountainCopy));
            debugPrint(Arrays.deepToString(mountainHeights));
            riverHeight+=1;
            testSet.addAll(bankSet);
            debugPrint(testSet.toString());
            while(!testSet.isEmpty()){
                debugPrint(testSet.toString());
                Point nextRiverPosition = testSet.remove();
                debugPrint("Next position is:" + nextRiverPosition.toString());
                //3. for each point in the test set
                //     *      a. find all neighbours (as points) and check if height is lower than water level
                //     *      b. if height lower, add point to the river, bank and test sets
                //     *      c. if height higher, add this point to the bank set, and continue. at end, any positions not banked should be rmeoved

                boolean stillBank = false;
                for(Point neighbour: getNeighboursPoints(nextRiverPosition.x,nextRiverPosition.y,mountainHeights)){
                    if(mountainCopy[neighbour.x][neighbour.y] != '-'){
                        if(mountainHeights[neighbour.x][neighbour.y] <riverHeight){
                            mountainHeights[neighbour.x][neighbour.y] = 0;  //now part of river
                            mountainCopy[neighbour.x][neighbour.y] = '-';  //now part of river
                            bankSet.add(neighbour);
                            if(!testSet.contains(neighbour)){testSet.add(neighbour);}
                        }
                        else{
                            stillBank = true;  //there exists an adjoining neighbour higher than current river == a bank

                        }
                    }
                }
                if(!stillBank){
                    bankSet.remove(nextRiverPosition);
                }
            }
            availableLandings.add(getAvailablePositions(mountainCopy));
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
    public static int getAvailablePositions(char[][] mountain){
        int posns =0;
        for(char[] row: mountain){
            for(char posn: row){
                if(posn != '-'){ posns++;}
            }
        }
        return posns;
    }

    public static int peakHeight(char[][] mountain) {
        int[][] copy = convertMountainToHeights(mountain);
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

    public static char[][] deepCopyMountain(char[][] mountain){
        char[][] mountainCopy = new char[mountain.length][mountain[0].length];
        IntStream.range(0, mountain.length).
                forEach(x -> mountainCopy[x] = Arrays.copyOf(mountain[x],mountain[x].length));
        return mountainCopy;
    }

    public static int[][] convertMountainToHeights(char[][] mountain){
        int[][] mountainCopy = new int[mountain.length][mountain[0].length];
        IntStream.range(0, mountain.length).
                forEach(x -> IntStream.range(0,mountain[x].length)
                        .forEach(y ->{
                            if(mountain[x][y] == '^'){mountainCopy[x][y] = -1;}
                            else{ mountainCopy[x][y] = 0;}
                        }));
        return mountainCopy;
    }

    public static void debugPrint(String msg){
        if(debug){
            System.out.println(msg);
        }
    }

    public static String mountainPrint(char[][] mountain){
        StringBuilder result = new StringBuilder();
        for(char[] row:mountain){
            result.append(Arrays.toString(row) + "\n");
        }
        return result.toString();
    }

}




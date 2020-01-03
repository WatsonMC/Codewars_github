package code;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BIrdMountainNew {


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




package code;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.IntStream;

public class Boggle {

    private final char[][] board;
    private boolean valid;
    public Boggle(final char[][] board, final String word) {
//        System.out.println(printArray(board));
        // Your code here!
        /**
         * Let first letter of word = start_letter, let i = index of letters in the words
         *  1. Find all start_letters within board, and for each start_letter position
         *      a. let neigbours  = moore neighbourhood of the current letter
         *      b. for any neigbour = word[i+1], set i =i+1, word = word{i+1 ->end}, move to step a.
         *      c. if no neighbours = word[i+1] ret false
         *      d. if i = word.length() return true
        *  2. return or(start_letter's results)
         */
        this.board = board;
        char startLetter = word.charAt(0);
        List<Point> startPoints = new ArrayList<>();
        IntStream.range(0,board.length).forEach(x ->{IntStream.range(0, board[0].length).forEach(y ->{if(board[x][y] == startLetter){startPoints.add(new Point(x,y));}});});
        valid = false;
       valid = startPoints.stream().map(p -> recursiveCheck(0,word,p,new HashSet<>())).reduce(false,(a,b) -> a||b);
        for(Point point:startPoints){
            if(recursiveCheck(0,word,point, new HashSet<Point>())){
                valid = true;
                break;
            }
        }
    }

    public boolean recursiveCheck(int i, String word, Point startPoint, Set<Point> visited){
        visited.add(startPoint);
        if(i == word.length()-1){return true;}
        for(Point neighbour: getNeighbours(startPoint)){
            if(board[neighbour.x][neighbour.y] == word.charAt(i+1)&& !visited.contains(neighbour)){
                if(recursiveCheck(i+1,word,neighbour,new HashSet<>(visited))){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Point> getNeighbours(Point point){
        List<Point> result  =  new ArrayList<>();
        int n = board.length;
        int m = board[0].length;
        for(int x = point.x == 0 ? 0:point.x-1;x<point.x+2&&x<n;x++){
            for(int y = point.y==0 ? 0: point.y-1;y<point.y+2&&y<m;y++){
                result.add(new Point(x,y));
            }
        }
        result.remove(point);
        return result;
    }

    public String printArray(char[][] array){
        StringBuilder result = new StringBuilder();
        for(char[] row:array){
            result.append(Arrays.toString(row) + "\n");
        }
        return result.toString();
    }



    public boolean check() {
        // Your code here too!
        return valid;
    }
}

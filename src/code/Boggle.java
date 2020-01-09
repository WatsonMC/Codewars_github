package code;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.stream.IntStream;

public class Boggle {

    private final char[][] board;
    private boolean valid;
    public Boggle(final char[][] board, final String word) {
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
        for(Point point:startPoints){
            if(recursiveCheck(0,word,point)){
                valid = true;
            }
        }
    }

    public boolean recursiveCheck(int i, String word, Point startPoint){
        if(i == word.length()-1){return true;}    //exit condition
        for(Point neighbour: getNeighbours(startPoint)){
            if(board[neighbour.x][neighbour.y] == word.charAt(i+1)){
                if(recursiveCheck(i+1,word,neighbour)){
                    return true;
                };
            }
        }
        return false;
    }

    public List<Point> getNeighbours(Point point){
        List<Point> result  =  new ArrayList<>();
        int n = board.length;
        int m = board[0].length;
        for(int a = -1;a <2;a+=2){
            if(point.x +a >=0 && point.x+ a<n){
                result.add(new Point(point.x+a,point.y));
            }
            if(point.y+a>=0 && point.y+a <m){
                result.add(new Point(point.x,point.y+a));
            }
        }
        return result;
    }

    public boolean check() {
        // Your code here too!
        return valid;
    }
}

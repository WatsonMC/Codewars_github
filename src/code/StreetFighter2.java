package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StreetFighter2 {
    /**
     * Inputs
     *  List of names (2x6)
     *  Cursor starts at 0,0
     *  List of moves supplied (up down left right)
     *
     *  Outputs
     *    List of characters who've been hovered over
     *
     *  Rules
     *   Horizontal rollover is possible
     *   Vertical rollover not possible
     */

    private static Point pointer = new Point();
    private static  int X_LIMIT;   //0-2
    private static  int Y_LIMIT;    //0-5

    public static String[] superStreetFighterize(String[][] fighters, int[] position, String[] moves) {
        pointer.x = position[0];
        pointer.y = position[1];

        X_LIMIT = fighters.length-1;
        Y_LIMIT = fighters[0].length-1;

        List<String> resultList = new ArrayList<>();
        for(String move: moves){
            pointer = performMove(pointer,move,fighters);
            resultList.add(fighters[pointer.x][pointer.y]);
        }
        return resultList.stream().toArray(String[]::new);

    }

    public static Point performMove(Point currentPointer, String move, String[][] fighters) {
        int x=  currentPointer.x;
        int y = currentPointer.y;
        switch (move) {
            case "up":
                x = currentPointer.x-1<0?0:currentPointer.x-1;
                return fighters[x][y].equals("") ? currentPointer: new Point(x,y);
            case "down":
                x = currentPointer.x+1>X_LIMIT?X_LIMIT:currentPointer.x+1;
                return fighters[x][y].equals("") ? currentPointer: new Point(x,y);
            case "left":
                for(int i =0; i<Y_LIMIT;i++){
                    y = (currentPointer.y+ Y_LIMIT-i)%(Y_LIMIT+1);
                    if(!fighters[x][y].equals("")){
                        return new Point(x,y);
                    }
                }
                return currentPointer;
            case "right":
                for(int i =0; i<Y_LIMIT;i++){
                    y = (currentPointer.y+1+i)%(Y_LIMIT+1);
                    if(!fighters[x][y].equals("")){
                        return new Point(x,y);
                    }
                }
                return currentPointer;
            default:
                return null;
        }
    }
}

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

    public static String[] streetFighterSelection(String[][] fighters, int[] position, String[] moves) {
        pointer.x = position[0];
        pointer.y = position[1];

        X_LIMIT = fighters.length-1;
        Y_LIMIT = fighters[0].length-1;

        List<String> resultList = new ArrayList<>();
        Point nextPoint = new Point();
        for(String move: moves){
            nextPoint = performMove(pointer,move);
            pointer = fighters[nextPoint.x][nextPoint.y].equals("") ? pointer:nextPoint;
            resultList.add(fighters[pointer.x][pointer.y]);
        }
        return resultList.stream().toArray(String[]::new);

    }

    public static Point performMove(Point currentPointer, String move, String[][] fighters) {
        int x;
        switch (move) {
            case "up":
                x = currentPointer.x-1<0?0:currentPointer.x-1;
                return new Point(x,currentPointer.y);
            case "down":
                x = currentPointer.x+1>X_LIMIT?X_LIMIT:currentPointer.x+1;
                return new Point(x,currentPointer.y);
            case "left":
                return new Point(currentPointer.x ,(currentPointer.y+ Y_LIMIT)%(Y_LIMIT+1));
            case "right":
                return new Point(currentPointer.x ,(currentPointer.y+1)%(Y_LIMIT+1));
            default:
                return null;
        }
    }
}

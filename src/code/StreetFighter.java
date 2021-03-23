package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StreetFighter {
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
    private static final int X_LIMIT = 1;   //0-2
    private static final int Y_LIMIT =5;    //0-5

    public static String[] streetFighterSelection(String[][] fighters, int[] position, String[] moves) {
        pointer.x = position[0];
        pointer.y = position[1];
        List<String> resultList = new ArrayList<>();
        for(String move: moves){
            pointer = performMove(pointer,move);
            resultList.add(fighters[pointer.x][pointer.y]);
        }
        return resultList.stream().toArray(String[]::new);

    }

    public static Point performMove(Point currentPointer, String move) {
        switch (move) {
            case "up":
                return new Point(0,currentPointer.y);
            case "down":
                return new Point(1,currentPointer.y);
            case "left":
                return new Point(currentPointer.x ,(currentPointer.y+ Y_LIMIT)%(Y_LIMIT+1));
            case "right":
                return new Point(currentPointer.x ,(currentPointer.y+1)%(Y_LIMIT+1));
            default:
                return null;
        }
    }
}

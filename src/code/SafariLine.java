package code;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SafariLine {
    public static boolean line(final char[][] grid) {
        // Your code here

        /**
         * Need to handle:
         *  1. ALl line segments must be in path
         *  2. corners have to change direction, can't be used to go straight
         */

        return false;
    }

    public Integer findNumPathes(Point startPoint, char[][] grid, Set<Point> visited, Integer currPathes){
        visited.add(startPoint);    //prevent looping
        List<Point> neighbours = getNeighbours(startPoint,grid);
        /**
         * exit conditions
         *  - no further pathes (either empty list or all elements previsited) ->false
         *  - neighbours contains X, not visited -> pass
         */
        return 1;

    }


    public List<Point> getNeighbours(Point startPoint, char[][] grid) {
        /**
         * if '-', left and right
         * if '|' up and down
         * if '+' up down left right
         */
        List<Point> result = new ArrayList<>();
        switch (grid[startPoint.x][startPoint.y]) {
            case '|':
                result.addAll(getAboveIfValid(startPoint,grid));
                break;
            case '-':
                result.addAll(getBesideIfValid(startPoint,grid));
                break;
            case '+':
            case 'X':
                result.addAll(getBesideIfValid(startPoint,grid));
                result.addAll(getAboveIfValid(startPoint,grid));
                break;
        }
        return result;
    }

    public List<Point> getAboveIfValid(Point startPoint, char[][] grid) {
        List<Point> result = new ArrayList<>();
        for (int a = -1; a < 2; a += 2) {
            if (startPoint.x + a >= 0
                    && startPoint.x + a < grid.length
                    && (grid[startPoint.x + a][startPoint.y] == '|'
                    || grid[startPoint.x + a][startPoint.y] == '+'
                    || grid[startPoint.x + a][startPoint.y] == 'X')) {
                result.add(new Point(startPoint.x + a, startPoint.y));
            }
        }
        return result;
    }

    public List<Point> getBesideIfValid(Point startPoint, char[][] grid) {
        List<Point> result = new ArrayList<>();
        for (int a = -1; a < 2; a += 2) {
            if (startPoint.y + a >= 0
                    && startPoint.y + a < grid[0].length
                    && (grid[startPoint.x][startPoint.y + a] == '-'
                    || grid[startPoint.x][startPoint.y + a] == '+'
                    || grid[startPoint.x][startPoint.y + a] == 'X')) {
                result.add(new Point(startPoint.x, startPoint.y + a));
            }
        }
        return result;
    }
}

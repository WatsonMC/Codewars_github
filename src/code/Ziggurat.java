package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ziggurat {


    //need a class structure for the ziggaruat which holds
    // position of each switch
    // state of each switch
    //      Each switch needs to reveal methods for:
    //          returning direction when prompted based on input direction
    //  zigaraund needs to
    //      take input of door
    //      iterate accross moves
    //      return exit point or null if they exit  west
    public static List<Point> rideOfFortune(String[] artifact, int[] explorers){
        //parse artifact into data structure
        ZiggarautInstance art = new ZiggarautInstance(artifact);
        List<Point> res  = new ArrayList<>();
        for(int explorer:explorers){
            res.add(art.explore(explorer));
        }
        return res;
//        return Arrays.stream(explorers)
//                .mapToObj(a->art.explore(a))
//                .collect(Collectors.toList());
    }

    public static class ZiggarautInstance {
        /**     0
         *      |
         *      |
         *3 ----------- 1
         *      |
         *      |
         *      2
         */
        final int NORTH = 0;
        final int EAST = 1;
        final int SOUTH  = 2;
        final int WEST = 3;
        char[][] posns;
        final int n;
        public ZiggarautInstance(String[] artifact){
            n = artifact.length;
            posns = new char[n][n];
            IntStream.range(0,n)
                    .forEach(a -> IntStream.range(0,n)
                    .forEach(b -> posns[a][b] = artifact[a].charAt(b)));
        }

        public Point explore(int door){
            int direction = EAST;
            int nextX = door;
            int nextY = 0;
            Integer counter = 0;
            while(true){
                Point location = new Point(nextX,nextY);
                direction = getDirection(location, direction);

                switch(direction){
                    case EAST:
                        nextY++;
                        break;
                    case WEST:
                        nextY--;
                    case NORTH:
                        nextX--;
                    case SOUTH:
                        nextX++;
                    default:
                        counter++;
                        System.out.println("looping + " + location.toString() + " " + direction);
                        break;
                }
                if(nextY>=n||nextX>=n||nextX<0){
                    return location;
                }
                if(nextY<0){return null;}
            }
        }

        public int getDirection(Point p, int direction){
            switch(posns[p.x][p.y]){
                case 'A':
                    posns[p.x][p.y] = 'B';
                    /**
                     * 0
                     */
                    return (direction+4-1)%4;

                case 'B':
                    posns[p.x][p.y] = 'A';
                    return (direction+1)%4;

                /**
                 * 1+4 -1 =4%4 =2
                 * 2+4-1 = 1
                 * 2
                 * 3
                 *
                 */
                default:
                    return direction;
                }
        }
    }
}

package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Spiralizor {
    /**
     * ---------->x
     *\
     * \
     *  \
     *   V
     *    Y
     * @param size
     * @return
     */
    public static int[][] spiralize(int size) {
        int n = size;
        int iArm = n-1;
        Point pj = new Point(0,0);
        List<Point> points = new ArrayList<>();
        points.add(pj);
        int[][] spiral = new int[n][n]; //default values are 0

        int direction = 0;

        for(int i = 0; i< n;i++){
            if(i!=0){
                iArm = (n-1) - 2*((i-1)/2);
                direction = (direction+1)%4;
            }
            for(int j = 0;j<iArm; j++){
                pj = getNextPoint(direction,pj);
                points.add(new Point(pj));
            }
        }

        points.stream().forEach(a-> spiral[a.x][a.y] = 1);
        System.out.println(spiral[0][0]);
        return spiral;
    }

    public static Point getNextPoint(int direction, Point current){
        switch (direction){
            case 0:
                return new Point(current.x,current.y+1);
            case 1:
                return new Point(current.x+1,current.y);
            case 2:
                return new Point(current.x, current.y-1);
            case 3:
                return new Point(current.x-1,current.y);
            default:
                return current;
        }
    }
}

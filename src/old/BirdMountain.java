package old;

import java.util.stream.IntStream;

public class BirdMountain {


    /**
     * ^^^^
     * ^^^
     * ^
     * ^^^^
     *    ^
     *Conditions for position (x,y) to increment
     * Min
     *
     * 1.Tops and bottoms  = 1
     * 2. for each row
     *  a. left and right edge = 1
     *  b. from left and right, each position  = 1 iff row above or below is empty
     * 3. cycle from i= 2 -> n for rest of rows
     *  - We know that any unset position is now >1, and we have ID'ed all ones
     *  - We now have to run left-right, setting to posn's to i if
     *         - max(surrounds) = i-1 || i
     *         - min(surrounds) = i-1 => if min surrounds are i, this one is i+1
     *   - exit conditiions are all rows completed
     *
     *BETTER WAY:
     * for each posn (x,y):
     * find no posns above/below/left/right with consecutive ^'s.
     * value = min(left,above,right,below)
     * @param mountain
     * @return
     */

    static final char PEAK = '^';
    public static int peakHeight(char[][] mountain) {
        // Your code here
        int[][] heights = new int[mountain.length][mountain[0].length];
        int max = 0;
        for(int x = 0;x<mountain.length;x++){
            final int X = x;
            max = Math.max(max,
                    IntStream.range(0,mountain[0].length)
                            .map(y-> mountain[X][y]==PEAK?getHeight(mountain,X,y):0)
                            .reduce(0, (a,b) ->Math.max(a,b)));
        }

        return max;
    }

    public static int getHeight(char[][] mountain, int x,int y){
        int counter = 1;
    int xLim = mountain.length-1;
    int yLim = mountain[0].length-1;
    char tileValue = PEAK;
        while(true){
        if((x-counter<0 || mountain[x-counter][y]!= PEAK)
                ||(x+counter>xLim || mountain[x+counter][y]!= PEAK)
                ||(y+counter>yLim || mountain[x][y+counter]!=PEAK)
                ||(y-counter<0 || mountain[x][y-counter]!=PEAK)){
            return counter-1 == 0?1:counter-1;
        }
        counter++;
    }
}

}

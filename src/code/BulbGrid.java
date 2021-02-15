package code;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BulbGrid {

    //static{ Preloaded.withCanvas=false; }  // use this if you have troubles with buffer error limit

    static int i=0;
    static int[][][] exampleSolutions = {    // solutions for the first basic tests. You can remove them once you're done...
            {{1,3},{5,3}},                       // Note: works only for the sample tests, not the full test suite
            {{1,3},{3,5},{5,3}},                 // (because the random tests may be done first)
            {{1,3},{5,3},{7,5}},
            {{1,3},{5,3},{7,5},{7,1},{2,1}},
            {{1,3},{5,3},{7,5},{7,1},{2,1},{5,4}},
    };

    public static List<Point> switchBulbs(String gameMap) {
        return new BulbGrid().switchBulbsNonStatic(gameMap);
        //Coding like playing game
//        return i>=exampleSolutions.length ? null :  Arrays.stream(exampleSolutions[i++])
//                .map( a -> new Point(a[0],a[1]) )
//                .collect(Collectors.toList());

    }


    public List<Point> switchBulbsNonStatic(String gameMap){
        List<BulbGrid.Bulb> bulbs = new ArrayList<>();
        // turn the gameMap into a list of bulbs
        String[] gameMapRows = gameMap.split("\n");
        for(int i=0;i<gameMapRows.length;i++){
            for(int j = 0; j<gameMapRows[i].length(); j++){
                if(gameMapRows[i].charAt(j) == 'B'){
                    bulbs.add(new Bulb(i-1,j-1));
                }
            }
        }

        List<Bulb> visited = new ArrayList<>();
        List<Bulb> unvisited = new ArrayList<>();
        unvisited.addAll(bulbs);
        List<Point> path = new ArrayList<>();
        Bulb currBulb = unvisited.remove(0);

        return recursiveFindPath(path, unvisited,bulbs,currBulb);

        //return null;
//        return i>=exampleSolutions.length ? null :  Arrays.stream(exampleSolutions[i++])
//                .map( a -> new Point(a[0],a[1]) )
//                .collect(Collectors.toList());

    }

    public List<Point> recursiveFindPath(List<Point> currPath, List<Bulb> unvisited, List<Bulb> bulbs, Bulb currentBulb){

        // base case
        if(unvisited.isEmpty()){ return currPath;}
        List<Bulb> viableBulbs = currentBulb.viableBulbs(unvisited);

        return null;
    }

    static class Bulb{

        int x;
        int y;
        Point bulbPoint;

        public Bulb(int x, int y){
            this.x = x;
            this.y = y;
            this.bulbPoint = new Point(x,y);
        }



        public List<Bulb> viableBulbs(List<Bulb> bulbs){
            List<Bulb> viable = new ArrayList<>();
            /**     x..x..x
             *      x..B..x
             *      x..x..x
             */
            Bulb leftBulb = new Bulb(0,10000);
            Bulb rightBulb = new Bulb(0,10000);
            Bulb upBulb = new Bulb(10000,10000);
            Bulb downBulb =     new Bulb(10000,10000);
            Bulb upLeft =     new Bulb(10000,10000);
            Bulb upRight =     new Bulb(10000,10000);
            Bulb downLeft =     new Bulb(10000,10000);
            Bulb downRight =     new Bulb(10000,10000);

            for(Bulb bulb: bulbs){
                if(bulb.x == this.x && bulb.y <this.y){ // left
                    if(Math.abs(this.y - leftBulb.y)> Math.abs(this.y - bulb.y)){
                        viable.remove(leftBulb);
                        viable.add(bulb);
                        leftBulb = bulb;
                    }
                }
                if(bulb.x == this.x && bulb.y > this.y) { // right
                    if (Math.abs(this.y - rightBulb.y) > Math.abs(this.y - bulb.y)) {
                        viable.remove(rightBulb);
                        viable.add(bulb);
                        rightBulb = bulb;
                    }
                }
                if(bulb.y == this.y && bulb.x < this.x) { //above
                    if (Math.abs(this.x - upBulb.x) > Math.abs(this.x - bulb.x)) {
                        viable.remove(upBulb);
                        viable.add(bulb);
                        upBulb = bulb;
                    }
                }
                if(bulb.y == this.y && bulb.x > this.x) { //down
                    if (Math.abs(this.x - downBulb.x) > Math.abs(this.x - bulb.x)) {
                        viable.remove(downBulb);
                        viable.add(bulb);
                        downBulb = bulb;
                    }
                }
                if((Math.abs(bulb.x - this.x) == Math.abs(bulb.y - this.y))){
                    if(bulb.x < this.x && bulb.y <this.y){ //up left
                        if (Math.abs(this.x - upLeft.x) > Math.abs(this.x - bulb.x)) {
                            viable.remove(upLeft);
                            viable.add(bulb);
                            upLeft = bulb;
                        }
                    }
                    if(bulb.x< this.x && bulb.y >this.y){ // up right
                        if (Math.abs(this.x - upRight.x) > Math.abs(this.x - bulb.x)) {
                            viable.remove(upRight);
                            viable.add(bulb);
                            upRight = bulb;
                        }
                    }
                    if(bulb.x > this.x && bulb.y <this.y){ //bottom left
                        if (Math.abs(this.x - downLeft.x) > Math.abs(this.x - bulb.x)) {
                            viable.remove(downLeft);
                            viable.add(bulb);
                            downLeft = bulb;
                        }
                    }
                    if(bulb.x> this.x && bulb.y >this.y){ // bottom right
                        if (Math.abs(this.x - downRight.x) > Math.abs(this.x - bulb.x)) {
                            viable.remove(downRight);
                            viable.add(bulb);
                            downRight  = bulb;
                        }
                    }
                }
            }
            return viable;
        }

        @Override
        public boolean equals(Object o){
            if( o == this){return true;}
            if(!(o instanceof Bulb)){return false;}
            return this.equals((Bulb)o);
        }

        public boolean equals (Bulb b){
            return (b.x == this.x && b.y == this.y);
        }

    }



}

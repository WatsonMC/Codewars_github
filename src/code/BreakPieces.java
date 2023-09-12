package code;


import java.util.*;

public class BreakPieces {
    /**
     * DESIGN
     * Create a dia_grid object from the shape
     *  dia_grid is a set of nodes, each node with an x,y coordinate
     * Traverse the grid x->, y-> starting at 0,0
     *  identify start of a shape with pattern:
     *  + -
     *  |'' <--- this space, with a boundary on neighbours (-1,-1),(0,-1),(-1,0) is a start of shape ID
     *
     */


    /**
     * @param shape
     * @return
     */
    public static String[] process(String shape) {
        String[] empty = {"1"};

        DiaGrid grid = new DiaGrid(shape);
        boolean inShape = false;
        int xLim;
        int yLim;
        //need to iterate through the grid coorinates
        //do I want a list of corindates based on an xy, or a list of node objects ordred in the order I want to process them

        xLim =grid.getXLim();
        yLim = grid.getYLim();

        //begin main loop through shapestrings
        for(int y = 0; y<yLim;y++){

            for(int x = 0; x < xLim ; x++){
                //cases:
                    // + do nffin
                    // | do nffn
                    // - do nffn
                    // "" do lots
                if(grid.getValue(x,y).equals(" ")){
                    //check if neighbour pattern mattches start of shape
                    // start of shape match
                    // +++, |+-,|++,++-  <== yes
                    // -+|, ---
                    //thats defined by
                        //(x-1,y-1),(x,y-1),(x-1,y) are all
                    List<Node> neighbours= grid.getNeighbours(x,y);

                }
            }
        }





        return empty;
    }

    public static class DiaGrid{
        /**
         * Grid is repped by a map of Integer, Map<Integer,Node>
         * each integer in map level 1 is the 'y' dimension
         * each integer in map level 2 is the 'x' dimension
         *
         *  |  ----->x
         *  | + - - - +
         *  | |       |
         *  | + - - - +
         * \/
         *  y
         *
         */
        Map<Integer, Map<Integer,Node>> grid;

        public DiaGrid(String diagram){
            grid = new HashMap<>();
            String[] lines = diagram.split("\n");
            for(int y =0; y<lines.length;y++){
                String[] line = lines[y].split("");
                for(int x=0; x<line.length;x++){
                    if(!grid.keySet().contains(y)){
                        grid.put(y,new HashMap<>());
                    }
                    grid.get(y).put(x, new Node(x,y,line[x]));
                }
            }
        }

        /**
         * takes an x,0 co-ord and returns a list of neighbour nodes starting left 1 of the given coords and moving clockwise around them. as:
         *          ^>>>>>>>
         *         ^ + - -
         *        ^  | X
         *          |
         *
         *  Where the neighbours exist the nodes from the grid are retrned. ELse a node with the imaginary neighbours coords and an isnull status are returned.
         *  List order is maontained
         * @param x
         * @param y
         * @return
         */
        public List<Node> getNeighbours(int x, int y){
            int[] x_i = {
                    x-1,x-1,x,x+1,x+1,x+1,x,x-1
            };
            int[] y_i = {
                    y,y-1,y-1,y-1,y,y+1,y+1,y+1
            };
            List<Node> neigbours = new ArrayList<>();
            for(int i =0;i<6;i++){
                if (!this.getValue(x, y).equals("N")) {
                    //mange not null
                    neigbours.add(this.getNode(x_i[i],y_i[i]));
                }
                else{
                    //manage  null
                    neigbours.add(new Node(x_i[i],y_i[i],"N"));
                }
            }
            return neigbours;
        }

        public Node getNode(int x, int y){
            if(getValue(x,y) .equals("N")){
                System.out.println("ERROR: getNode on null node");
                return  new Node(-1,-1,"N");
            }
            Node target = grid.get(y).get(x);
            return  new Node(target.x, target.y, target.value);
        }

        /**
         * returns "N" if not part of grid
         * @param x
         * @param y
         * @return
         */

        public String getValue(int x, int y){
            if(grid.keySet().contains(y) && grid.get(y).keySet().contains(x)){
                return grid.get(y).get(x).value;
            }
            return "N";
        }

        public String printGrid(){
            StringBuilder sb = new StringBuilder();
            for(Integer y: grid.keySet()){
                for(Integer x:grid.get(y).keySet()){
                    sb.append(getValue(x,y));
                }
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() -1);
            return sb.toString();
        }

        public int getXLim(){
            return grid.get(0).size();
        }

        public int getYLim(){
            return grid.size();
        }
    }


    public static class Node{
        Integer x;
        Integer y;
        String value;
        private boolean isNull;

        public Node (Integer x, Integer y, String value){
            this.x = x;
            this.y = y;
            this.value = value;
            if(value.equals("N")){
                isNull=true;
            }else{
                isNull=false;
            }
        }
        public boolean IsNull(){return this.isNull;}
    }


    //TEst Classes
    public DiaGrid gridGen(String gridString){
        return new DiaGrid(gridString);
    }
}

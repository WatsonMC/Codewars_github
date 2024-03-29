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
        String[] shapeStrings;

        Set<Set<Node>> shapes = new HashSet<>();
        Set<Node> shapeNodes = new HashSet<>();
        DiaGrid grid = new DiaGrid(shape);
        int xLim;
        int yLim;

        xLim =grid.getXLim();
        yLim = grid.getYLim();

        //begin main loop through shapestrings
        for(int y = 0; y<yLim;y++){
            for(int x = 0; x < xLim ; x++){
                Node current = grid.getNode(x,y);
                if(current.value.equals(" ") && !shapeNodes.contains(current)){
                    List<Node> neighbours= grid.getNeighbours(x,y);
                    String cornerString =neighbours.get(0).value + neighbours.get(1).value + neighbours.get(2).value;
                    if(matchCorner(cornerString)){
                        Set<Node> newShape = traverseShape(current,grid);
                        shapes.add(traverseShape(current,grid));
                        shapeNodes.addAll(newShape);
                    }
                }
            }
        }
        shapeStrings = shapes.stream().map(x ->printShape(x)).toArray(String[]::new);

        return shapeStrings;
    }

    /**
     * Creates a print string of the shape set passed
     * @param shape
     * @return
     */
    public static String printShape(Set<Node> shape){
        //Creates an X,Y nested list of characters which will be populatd with the edges of the shape
        Set<Node> edges = new HashSet<>();
        int min_x = 10000;
        int min_y = 10000;
        int max_x= 0;
        int max_y = 0;
        for(Node curNode: shape){
            int[][] neighBourCoord = DiaGrid.getNeighbourCoords(curNode.x, curNode.y);
            for(int i=0; i<8; i++){
                int x = neighBourCoord[i][0];
                int y = neighBourCoord[i][1];
                min_x = x<min_x ? x:min_x;
                min_y = y<min_y ? y:min_y;
                max_x = x+1>max_x ? x+1:max_x;
                max_y = y+1>max_y ? y+1:max_y;
                if(!shape.contains(new Node(x,y," "))){
                    edges.add(new Node(x,y,"+"));
                }
            }
        }

        String[][] printArr =  new String[max_y][max_x];
        for(Node edgeNode: edges){
            printArr[edgeNode.y][edgeNode.x] = getEdgeType(edgeNode,edges);
        }
        StringBuilder sb =  new StringBuilder();
        for(int y_i = min_y; y_i<max_y; y_i++){
            for(int x_i = min_x; x_i<max_x; x_i++){
                sb.append(printArr[y_i][x_i] == null ? " ":printArr[y_i][x_i]);
            }
            sb.append("\n");
        }
        return sb.deleteCharAt(sb.length()-1).toString().replaceAll("\\s+\\n","\n").replaceAll("\\s+$","");
    }

    /**
     * evaulates the given edge node against supplied edge nodes and returns the required shape edge.
     * Either '-', '+' or '|'
     * Edge nodes shoujld be of undefined value'+'
     * @param edgeNode
     * @param edgeNodes
     * @return
     */
    public static String getEdgeType(Node edgeNode, Set<Node> edgeNodes){
        int[][] neighbours = DiaGrid.getNeighbourCoords(edgeNode.x,edgeNode.y);
        Node left = new Node(neighbours[0][0],neighbours[0][1],"+");
        Node right= new Node(neighbours[4][0],neighbours[4][1],"+");
        Node up= new Node(neighbours[2][0],neighbours[2][1],"+");
        Node down= new Node(neighbours[6][0],neighbours[6][1],"+");
        if((edgeNodes.contains(up) && edgeNodes.contains(down)) && !(edgeNodes.contains(left) || edgeNodes.contains(right))){
            return "|";
        }else if((edgeNodes.contains(left) && edgeNodes.contains(right)) && !(edgeNodes.contains(up) || edgeNodes.contains(down))){
            return "-";
        }
        else return "+";
    }

    /**
     * Takes a node and a grid object and finds it's extents.
     * Adds any neighbour nodes which are space to a 'to check' list, adding each to the shape
     * Finishes when no new space nodes exist in current ndoe neighbours and to check list is empty
     *
     * Returns list of Nodes from the grid constituting the shape
     * @param node
     * @param grid
     * @return
     */
    public static Set<Node> traverseShape(Node node, DiaGrid grid){
        Set<Node> shapeNodes = new HashSet<>();
        List<Node> neighbours;
        List<Node> toCheck = new ArrayList<>();
        Node current;
        toCheck.add(node);
        while (! toCheck.isEmpty()){
            current = toCheck.remove(0);
            shapeNodes.add(current);
            neighbours = grid.getNeighbours(current.x, current.y);
            for(Node neighbour :neighbours){
                if(!toCheck.contains(neighbour) && !shapeNodes.contains(neighbour) &&  neighbour.value.equals(" ")){
                    toCheck.add(neighbour);
                }
            }
        }
        return new HashSet<>(shapeNodes);
    }

    public static boolean matchCorner(String cornerString){return cornerString.matches("(\\+\\+\\+)|(\\+\\+\\-)|(\\|\\+\\-)|(\\|\\+\\+)");}

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
         *  List order is
         * @param x = node x posn
         * @param y = node y posn
         * @return List of Nodes in order shown aove
         */
        public List<Node> getNeighbours(int x, int y){
            int[][] x_y = getNeighbourCoords(x,y);
            int x_i;
            int y_i;

            List<Node> neigbours = new ArrayList<>();
            for(int i =0;i<8;i++){
                x_i = x_y[i][0];
                y_i = x_y[i][1];
                if (!this.getValue(x_i ,y_i).equals("N")) {
                    //mange not null
                    neigbours.add(this.getNode(x_i,y_i));
                }
                else{
                    //manage  null
                    neigbours.add(new Node(x_i,y_i,"N"));
                }
            }
            return neigbours;
        }


        /**
         * Gets an int array of x,y coords for neighgours of a node based on co-ords
         * @param x
         * @param y
         * @return
         */
        public static int[][] getNeighbourCoords(int x, int y){
            int[][] result = {
                    new int[] {x-1,y},
                    new int[] {x-1,y-1},
                    new int[] {x,y-1},
                    new int[] {x+1,y-1},
                    new int[] {x+1,y},
                    new int[] {x+1,y+1},
                    new int[] {x,y+1},
                    new int[] {x-1,y+1},
                    new int[] {x-1,y+1},
            };
            return result;

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
         * @return Either the node value, or 'N' if co-ordinates are not part of grid
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

        @Override
        public String toString(){
            return "(" + this.x + ", " + this.y + "):" + this.value;
        }

        @Override
        public int hashCode(){
            int charValue = this.value.charAt(0);
            return this.x + this.y + charValue;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Node){
                return this.equals((Node) o);
            }else{
                return false;
            }
        }
        public boolean equals(Node newNode){
            return  newNode.x == this.x &&  newNode.y == this.y && newNode.value.equals(this.value) && this.isNull == newNode.isNull;
        }
    }


    //TEst Classes
    public DiaGrid gridGen(String gridString){
        return new DiaGrid(gridString);
    }
}

package code;

import java.util.*;
import java.util.stream.Collectors;


public class Blobservation{
    int width;
    int height;
   public List<Blob>[][] blobsOnBoard;    //array of list of blobs
    final int BLOB_MAX_SIZE = 20;

    public Blobservation(int height   , int width){
        //TODO Throw exception
        this.width = width;
        this.height = height;
        this.blobsOnBoard = new ArrayList[width][height];
        for(int x =0; x<height; x++){
            for(int y= 0; y<width; y++){
                blobsOnBoard[x][y] = new ArrayList<Blob>();
            }
        }
    }

    public Blobservation(int width){
        //TODO Throw exception
       new Blobservation(width,width);
    }

    /**
     * Populate
     * 1. validate map
     * 2. For each map in list
     *  a. get x and y
     *  b. pull blobsOnBoard[x][y]
     *  c. add blob to list of blobs
     */
    public void populate(List<Map<String,Integer>> generation){
        //populate board
        if(!validateBlobList(generation)){ throw new IllegalArgumentException();}
//        generation.stream()
//                .map(a -> blobsOnBoard[a.get("x")][a.get("y")].add(new Blob(a.get("x"),a.get("y"), a.get("size"))));
        for(Map<String, Integer> blob:generation){
            int x = blob.get("x");
            int y = blob.get("y");
            int size = blob.get("size");
            blobsOnBoard[x][y].add(new Blob(x,y,size)); //add blob to list
        }
    }

    //test method for adding blobs withoud needing to create a generation list
    public void addBlob(int x,int y,int size){
        blobsOnBoard[x][y].add(new Blob(x,y,size));
    }
    public void addBlob(Blob blob){
        blobsOnBoard[blob.x][blob.y].add(blob);
    }
    public Blob makeBlob(int x,int y, int size){
        return new Blob(x,y,size);
    }


    /**
     * Move int x
     * 1. while x>0, for each move
     *  a. get list of blobs indexed by size
     *  b. find all blobs greater in size than smallest blob
     *  c. calculate their next movement and store in nextMove field
     *      - create list of blobs
     *  d. when complete, process all moves
     *      a. remoive from current location
     *      b. add to next location
     *      c. change x y
     *      d. reset nextMove
     *  e. When processing complete fuse all blobs
     *      a. for each positon on board call fuse
     *          - sum sizes of blobs in list
     *          - clear list
     *          - add new blob
     */

    public void move(int x){
        if(x<=0) {
            throw new RuntimeException();
        }
        for(int i =0; i<x;i++) {
            processMove();
            //System.out.println(board.printStateString() + "\n");
        }
    }

    private void processMove() {
        //fuseBlobs() -> this may need to be called before processing blobs since we can sometimes
        // populate the board before moving and have multiple blobs on a single posn
        Map<Integer, List<Blob>> blobsBySize = getBlobsBySize();
        if(blobsBySize.size()  == 0){logger("No blobs when processing move");}
        int smallestBlob = blobsBySize.keySet().stream().min(Integer::compareTo).get();//remove smallest blobs
        for(List<Blob> blobSizeSet: blobsBySize.values()){
            for(Blob blob:blobSizeSet){
                if(blob.size>smallestBlob){findNextMove(blob, blobsBySize);}
            }
        }
    }

    private void findNextMove(Blob blob, Map<Integer,List<Blob>> blobsBySize) {
        List<Blob> smallestDistanceTargets = getValidTargets(blobsBySize.values().stream().
                flatMap(List::stream)
                .collect(Collectors.toList()), blob);
        //first scan => closest blob
        if(smallestDistanceTargets.size() ==0){logger("Found no targets during first scan");}
        if (smallestDistanceTargets.size()==1){
            blob.setNextMove(smallestDistanceTargets.get(0));
            return;
        }
        //else find largest blobs which are closest => largest blobs of equal distance
        List<Blob> largestBlobs = getLargestBlobs(smallestDistanceTargets);
        if(largestBlobs.size() ==0){logger("Found no targets during first scan");}
        if (largestBlobs.size()==1){
            blob.setNextMove(largestBlobs.get(0));
            return;
        }
        //else find clockwise closest blob
        blob.setNextMove(getClockwiseClosestBlob(blob,largestBlobs));
    }

    public Blob getClockwiseClosestBlob(Blob source,List<Blob> largestBlobs) {
        Map<Double, Blob> blobAngles = new HashMap<>();
        for(Blob target:largestBlobs){
            double angle = getAngleBetweenBlobs(source,target);
            blobAngles.put(angle,target);
        }
        double minAngle = blobAngles.keySet().stream().min(Double::compareTo).get();
        return blobAngles.get(minAngle);
    }

    public double getAngleBetweenBlobs(Blob source, Blob target){
        double rawAngle = Math.atan2(target.y - source.y,target.x - source.x);
        return (3*Math.PI - rawAngle)%(2*Math.PI);
    }

    public static List<Blob> getLargestBlobs(List<Blob> blobs){
        int largest = blobs.stream().map(a->a.size).max(Integer::compareTo).get();
        return blobs.stream().filter(a->a.size == largest).collect(Collectors.toList());
    }

    public static List<Blob> getValidTargets(List<Blob> blobsOnBoard, Blob blob){
        int x = blob.x;
        int y = blob.y;
        int size = blob.size;
        int minDistance = blobsOnBoard.stream()
                .filter(a->a.x!=x && a.y!=y)
                .map(a -> Math.max(Math.abs(a.x-x), Math.abs(a.y - y)))
                .min(Integer::compareTo).get();
        List<Blob> result =  blobsOnBoard.stream()
                .filter(a -> Math.max(Math.abs(a.x-x), Math.abs(a.y - y)) == minDistance&&a.size<size)
                .collect(Collectors.toList());
        return result;
//        List<Blob> validTargets = new ArrayList<>();
//        int minDistance = Integer.MAX_VALUE;
//        for(Blob nextBlob: blobsOnBoard){
//            if(blob.size<size){
//                int distance = Math.max(Math.abs(nextBlob.x-x), Math.abs(nextBlob.y - y));
//                if(distance== minDistance){
//                    validTargets.add(nextBlob);
//                }
//                else if(distance<minDistance){
//                    validTargets.clear();
//                    validTargets.add(nextBlob);
//                }
//            }
//        }
//        return validTargets;
    }

    //tested
    public Map<Integer,List<Blob>> getBlobsBySize(){
        Map<Integer, List<Blob>> tempMap = new HashMap<>();
        for(int x =0; x<height; x++){
            for(int y= 0; y<width; y++){
                for(Blob blob: blobsOnBoard[x][y]){
                    if(!tempMap.containsKey(blob.size)){
                        tempMap.put(blob.size,new ArrayList<>());
                    }
                    tempMap.get(blob.size).add(blob);
                }
            }
        }
        return tempMap;
    }


    //need an easy way to fuse blobs on same board
    // -> array of lists of blobs
        //array of BoardPositions
        // Board positions hold a list of blobs
        // board positions have a fuse method
    // -> Raw list of blobs ->> will need to parse a loooootttt
    // -> Map of (x,y) to Lists -> no better than array since you ahve to manage all of the string references
    //move

    // print state
    public static void logger(String message){
        System.out.println(message);
    }

    //Tested
    public boolean validateBlobList(List<Map<String,Integer>> blobs){
        if(blobs.size() == 0){return false;}
        for(Map<String, Integer> blob: blobs){
            if(blob.get("x")<0||blob.get("x")>=height
                || blob.get("y")<0 || blob.get("y")>=width
                || blob.get("size")>20 || blob.get("size")<1) {
                return false;
            }
        }
        return true;
    }

   public String printState() {
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<width;i++) {
            for(int j =0; j<height;j++) {
                if(!blobsOnBoard[i][j].isEmpty()) {
//                    sb.append(blobsOnBoard[i][j].printState() + ", ");
                }
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.insert(0, "[");
        sb.insert(sb.length()-1, "]");
        return sb.toString();
    }

    public class Blob{
        Integer size;
        Integer x;
        Integer y;

        NextMove nextMove;

        public Blob(Map<String, Integer> blobDetails){
            if(blobDetails.size()>2 | blobDetails.size() <2){
                Blobservation.logger("Error: failed to create blob due to data");
                System.exit(1);
            }
            this.size = blobDetails.get("size");
            this.x = blobDetails.get("x");
            this.y = blobDetails.get("y");
        }

        public Blob(int x, int y, int size){
            this.size = size;
            this.x = x;
            this.y = y;
        }

        public NextMove getNextMove(){
            return this.nextMove;
        }

        //Equality of blobs
        @Override
        public boolean equals(Object o) {
            if(o instanceof Blob) {
                return this.equals((Blob) o);
            }
            return false;
        }

        public boolean equals(Blob o) {
            if(o.x == this.x && o.y == this.y && o.size == this.size) {
                return true;
            }
            return false;
        }

        public void setNextMove(Blob blob) {
            if(this.x == blob.x && this.y == blob.y) {logger("setNextMove called on blobs on same posn");}
            int xMovement = (blob.x - this.x)/Math.abs(this.x-blob.x);
            int yMovement = (blob.y - this.y)/Math.abs(this.y-blob.y);
            this.nextMove = new NextMove(this.x+ xMovement, this.y+ yMovement);
        }
    }

    public class NextMove{
        public final int x;
        public final int y;

        public NextMove(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //Equality of nextMoves
        @Override
        public boolean equals(Object o) {
            return o instanceof NextMove ? this.equals((NextMove)o):false;
        }
        public boolean equals(NextMove mv) {
            return this.x == mv.x && this.y == mv.y ? true:false;
        }
    }
}



/**
 * Blob board:
 * _______>y+
 * |
 * |
 * |
 * x+
 */







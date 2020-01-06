package code;

import java.util.*;
import java.util.stream.Collectors;


public class Blobservation1 {
    int width;
    int height;
    public List<Blob>[][] blobsOnBoard;    //array of list of blobs
    final int BLOB_MAX_SIZE = 20;

    public Blobservation1(int height, int width){
        this.width = width;
        this.height = height;
        this.blobsOnBoard = new ArrayList[height][width];
        for(int x =0; x<height; x++){
            for(int y= 0; y<width; y++){
                blobsOnBoard[x][y] = new ArrayList<Blob>();
            }
        }
    }

    public Blobservation1(int width){
        this(width,width);
    }

    public void populate(List<Map<String,Integer>> generation){
        if(!validateBlobList(generation)){ throw new IllegalArgumentException();}
        for(Map<String, Integer> blob:generation){
            int x = blob.get("x");
            int y = blob.get("y");
            int size = blob.get("size");
            blobsOnBoard[x][y].add(new Blob(x,y,size)); //add blob to list
        }
        fuseBlobs();
    }

    public void move(int x){
        if(x<=0) {
            throw new RuntimeException();
        }
        for(int i =0; i<x;i++) {
            if(!processMove()){return;}
        }
    }

    public void move(){
        move(1);
    }

    private boolean  processMove() {
        //fuseBlobs() -> this may need to be called before processing blobs since we can sometimes
        // populate the board before moving and have multiple blobs on a single posn
        boolean movesFound = false;
        fuseBlobs();
        Map<Integer, List<Blob>> blobsBySize = getBlobsBySize();
        if(blobsBySize.size()  == 0){
            return movesFound;
        }
        int smallestBlob = blobsBySize.keySet().stream().min(Integer::compareTo).get();//remove smallest blobs
        for(List<Blob> blobSizeSet: blobsBySize.values()){
            for(Blob blob:blobSizeSet){
                if(blob.size>smallestBlob){
                    findNextMove(blob, blobsBySize);
                    movesFound = true;
                }
            }
        }
        executeMoves();
        fuseBlobs();
        return movesFound;
    }

    private void findNextMove(Blob blob, Map<Integer,List<Blob>> blobsBySize) {
        List<Blob> blobsBySizeList = blobsBySize.values().stream().
                flatMap(List::stream)
                .collect(Collectors.toList());
        List<Blob> smallestDistanceTargets = getValidTargets(blobsBySize.values().stream().
                flatMap(List::stream)
                .collect(Collectors.toList()), blob);
        //first scan => closest blob
        if(smallestDistanceTargets.size() ==0){
            smallestDistanceTargets = getValidTargets(blobsBySize.values().stream().
                    flatMap(List::stream)
                    .collect(Collectors.toList()), blob);
        }
        if (smallestDistanceTargets.size()==1){
            blob.setNextMove(smallestDistanceTargets.get(0));
            return;
        }
        //else find largest blobs which are closest => largest blobs of equal distance
        List<Blob> largestBlobs = getLargestBlobs(smallestDistanceTargets);
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

        int largest = blobs.stream().map(a->a.size).reduce(blobs.get(0).size,(a,b) -> Math.max(a,b));
        return blobs.stream().filter(a->a.size == largest).collect(Collectors.toList());
    }

    public static List<Blob> getValidTargets(List<Blob> blobsOnBoard, Blob blob){
        int x = blob.x;
        int y = blob.y;
        int size = blob.size;
        int minDistance = blobsOnBoard.stream()
                .filter(a-> !(a.x==x && a.y==y))
                .filter(a->a.size<size)
                .map(a -> Math.max(Math.abs(a.x-x), Math.abs(a.y - y)))
                .reduce(Integer.MAX_VALUE,(a,b) -> Math.min(a,b));
        List<Blob> result =  blobsOnBoard.stream()
                .filter(a -> Math.max(Math.abs(a.x-x), Math.abs(a.y - y)) == minDistance&&a.size<size)
                .collect(Collectors.toList());
        return result;
    }

    public void executeMoves(){
        List<Blob>[][] newBlobsOnBoard = new ArrayList[height][width];
        for(int x =0; x<height; x++){
            for(int y= 0; y<width; y++){
                newBlobsOnBoard[x][y] = new ArrayList<Blob>();
            }
        }
        for(int x = 0; x<height;x++){
            for(int y = 0;y<width;y++){
                for(Blob blob: blobsOnBoard[x][y]){
                    if(blob.nextMove==null){
                        newBlobsOnBoard[x][y].add(blob);
                    }
                    else{
                        NextMove nextMove = blob.nextMove;
                        newBlobsOnBoard[nextMove.x][nextMove.y].add(new Blob(nextMove.x,nextMove.y,blob.size));
                    }
                }
            }
        }
        blobsOnBoard=newBlobsOnBoard;
    }

    public void fuseBlobs(){
        for(List<Blob>[] row: blobsOnBoard){
            for(List<Blob> posn:row){
                if(posn.size()>1){
                    posn = fuseBlobList(posn);
                }
            }
        }
    }

    public List<Blob> fuseBlobList(List<Blob> posn){
        int newBlobSize = posn.stream()
                .mapToInt(a ->a.size)
                .sum();
        int x = posn.get(0).x;
        int y = posn.get(0).y;
        posn.clear();
        posn.add(new Blob(x,y,newBlobSize));
        return posn;
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
    public List<List<Integer>> printState() {
        List<List<Integer>> blobs = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!blobsOnBoard[i][j].isEmpty()) {
                    for (Blob blob : blobsOnBoard[i][j]) {
                        List<Integer> blobDetails = new ArrayList<>();
                        blobDetails.add(blob.x);
                        blobDetails.add(blob.y);
                        blobDetails.add(blob.size);
                        blobs.add(blobDetails);
                    }
                }
            }
        }
        return blobs;
    }

    public class Blob{
        public Integer size;
        Integer x;
        Integer y;

        NextMove nextMove;

        public Blob(Map<String, Integer> blobDetails){
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
            int xMovement = this.x-blob.x == 0?0:(blob.x - this.x)/Math.abs(this.x-blob.x);
            int yMovement = this.y-blob.y == 0?0:(blob.y - this.y)/Math.abs(this.y-blob.y);
            this.nextMove = new NextMove(this.x+ xMovement, this.y+ yMovement);
        }

        public String print(){
            return "["+String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(size)+ "]";
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




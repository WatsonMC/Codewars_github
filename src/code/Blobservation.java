package code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Blobservation{
    int width;
    int height;
    List<Blob> blobsOnBoard;
    final int BLOB_MAX_SIZE = 20;

    public Blobservation(int height   , int width){
        this.width = width;
        this.height = height;
        this.blobsOnBoard  =  new ArrayList<>();
    }

    //populate
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

    private boolean validateBlobList(List<Map<String,Integer>> blobs){
        for(Map<String, Integer> blob: blobs){
            if(blob.get("x")<0||blob.get("X")>=height
                || blob.get("y")<0 || blob.get("y")>=width
                || blob.get("size")>20 || blob.get("size")<1) {
                return false;
            }
        }
        return true;
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
//class BlobBoard{
//    int width;
//    int height;
//
//    List<Blob> blobsOnBoard;
//    public BlobBoard(int height   , int width){
//        this.width = width;
//        this.height = height;
//        this.blobsOnBoard  =  new ArrayList<>();
//    }
//
//    public boolean addBlob(Blob blob){
//        if(blob.x<0|blob.x>=height||blob.y<0||blob.y>width){
//            Blobservation.logger(String.format("Failed to add blob to board, out of bounds: x,y => %d,%d", blob.x, blob.y);
//            return false;
//        }
//        blobsOnBoard.add(blob);
//        return true;
//    }
//
//
//
//}


class Blob{
    Integer size;
    Integer x;
    Integer y;
    public Blob(Map<String, Integer> blobDetails){
            if(blobDetails.size()>2 | blobDetails.size() <2){
                Blobservation.logger("Error: failed to create blob due to data");
                System.exit(1);
            }
            this.size = blobDetails.get("size");
            this.x = blobDetails.get("x");
            this.y = blobDetails.get("y");
    }

}





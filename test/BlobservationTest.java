import code.Blobservation;
import org.junit.Assert;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BlobservationTest {
    static  Blobservation b3x3;
    static  Blobservation.Blob b11;
    static Blobservation.Blob b22;
    static Blobservation.Blob b20;
    static Blobservation.Blob b00;
    static Blobservation.Blob b00Big;
    static List<Map<String,Integer>> generation1;

    @BeforeAll
    public static void seTup() {
        b3x3 = new Blobservation(3, 3);
        b11 = b3x3.makeBlob(1,1,7);
        b22 = b3x3.makeBlob(2,2,2);
        b20 = b3x3.makeBlob(2,0,5);
        b00 = b3x3.makeBlob(0,0,2);
        b00Big = b3x3.makeBlob(0,0,10);
        System.out.println(b20);

        b3x3.addBlob(b11);
        b3x3.addBlob(b22);
        b3x3.addBlob(b20);
        Blobservation b7x7 = new Blobservation(7, 7);
        generation1 = new ArrayList<>();
        generation1.add(createBlobMap(1,2,2));
        generation1.add(createBlobMap(1,1,5));
        generation1.add(createBlobMap(0,1,3));

    }

    public static Map<String, Integer> createBlobMap(int x, int y, int size){
        Map<String,Integer> b1map = new HashMap<>();
        b1map.put("x", x);
        b1map.put("y", y);
        b1map.put("size", size);
        return b1map;
    }

    @Test
    public void testGetBlobsBySize(){
        Map<Integer, List<Blobservation.Blob>> resultB3x3= new HashMap<>();
        resultB3x3.put(2,new ArrayList<>(Arrays.asList(b22)));
        resultB3x3.put(7,new ArrayList<>(Arrays.asList(b11)));
        resultB3x3.put(5,new ArrayList<>(Arrays.asList(b20)));
        Assert.assertEquals(resultB3x3,b3x3.getBlobsBySize());
    }

    @Test
    public void testValidateBlobList(){
        //blob outside of height/width
        Assert.assertTrue(b3x3.validateBlobList(generation1));
        List<Map<String,Integer>> gen1Fault = new ArrayList<>(generation1);
        gen1Fault.add(createBlobMap(1,6,2));
        Assert.assertFalse(b3x3.validateBlobList(gen1Fault));

        //blob smaller than 0
        List<Map<String,Integer>> gen1Fault2 = new ArrayList<>(generation1);
        gen1Fault2.add(createBlobMap(1,-1,1));
        Assert.assertFalse(b3x3.validateBlobList(gen1Fault2));

        //blob over size
        List<Map<String,Integer>> gen1Fault3 = new ArrayList<>(generation1);
        gen1Fault3.add(createBlobMap(1,1,25));
        Assert.assertFalse(b3x3.validateBlobList(gen1Fault3));
    }

    @Test
    public void testSetNextMove(){
        b11.setNextMove(b22);
        Assert.assertTrue((b11.getNextMove().x ==2 && b11.getNextMove().y == 2));
        b11.setNextMove(b00);
        Assert.assertTrue((b11.getNextMove().x ==0 && b11.getNextMove().y == 0));
    }

    @Test
    public void testGetValidTargets(){
        List<Blobservation.Blob> blobs = new ArrayList<>();
        blobs.add(b11);
        blobs.add(b22);
        blobs.add(b20);
        blobs.add(b00);
        List<Blobservation.Blob> expResult = new ArrayList<>();
        expResult.add(b22);
        expResult.add(b20);
        expResult.add(b00);

        Assert.assertEquals(expResult,Blobservation.getValidTargets(blobs,b11));
        expResult.clear();
        expResult.add(b11);
        Assert.assertEquals(expResult,Blobservation.getValidTargets(blobs,b00Big));
    }

    @Test
    public void testGetLargestTargets(){
        List<Blobservation.Blob> blobs = new ArrayList<>();
        blobs.add(b11);
        blobs.add(b22);
        blobs.add(b20);
        blobs.add(b00);
        List<Blobservation.Blob> expResult = new ArrayList<>();
        expResult.add(b11);
        Assert.assertEquals(expResult,Blobservation.getLargestBlobs(blobs));
    }
}

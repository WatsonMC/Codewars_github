import code.Blobservation;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlobservationTest {
    static  Blobservation b3x3;
    static  Blobservation b7x7;
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
        b7x7 = new Blobservation(7, 7);
        generation1 = new ArrayList<>();
        generation1.add(createBlobMap(1,2,2));
        generation1.add(createBlobMap(1,1,5));
        generation1.add(createBlobMap(0,1,3));

    }

    @After
    public void testFuze(){
        b7x7.addBlob(1,1,10);
        b7x7.addBlob(1,1,5);

        Assert.assertTrue(b7x7.blobsOnBoard[1][1].size()==2);
        b7x7.fuseBlobs();
        Assert.assertTrue(b7x7.blobsOnBoard[1][1].size()==1);
        Assert.assertTrue(b7x7.blobsOnBoard[1][1].get(0).size == 15);

    }

    public static Map<String, Integer> createBlobMap(int x, int y, int size){
        Map<String,Integer> b1map = new HashMap<>();
        b1map.put("x", x);
        b1map.put("y", y);
        b1map.put("size", size);
        return b1map;
    }

    @Test
    public void testGetClockwiseClosest(){
        List<Blobservation.Blob> blobs = new ArrayList<>();
        blobs.add(b11);
        blobs.add(b22);
        blobs.add(b20);
        blobs.add(b00);

        Blobservation.Blob s10 = b3x3.makeBlob(1,0,1);

        Assert.assertEquals(b00,b3x3.getClockwiseClosestBlob(s10,blobs));
        blobs.remove(b00);
        Assert.assertEquals(b11,b3x3.getClockwiseClosestBlob(s10,blobs));
        blobs.remove(b11);
        Assert.assertEquals(b22,b3x3.getClockwiseClosestBlob(b11,blobs));
    }

    @Test
    public void testPopulate(){
        //gen 1
        b7x7.populate(generation1);
        Assert.assertTrue(
                b7x7.blobsOnBoard[1][2].size()==1&&
                        b7x7.makeBlob(1,2,2).equals(b7x7.blobsOnBoard[1][2].get(0)));
        Assert.assertTrue(
                b7x7.blobsOnBoard[1][1].size()==1&&
                        b7x7.blobsOnBoard[1][1].get(0).equals(b7x7.makeBlob(1,1,5)));
        Assert.assertTrue(
                b7x7.blobsOnBoard[0][1].size()==1&&
                        b7x7.makeBlob(0,1,3).equals(b7x7.blobsOnBoard[0][1].get(0)));

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


        //                             {0,4,3}  =>  ["x"=0, "y"=4, "size"=3]
        private static int[][] gen0 = {{0,4,3}, {0,7,5},  {2,0,2}, {3,7,2}, {4,3,4},  {5,6,2}, {6,7,1}, {7,0,3}, {7,2,1}};
        private static int[][] gen1 = {{3,6,3}, {8,0,2},  {5,3,6}, {1,1,1}, {2,6,2},  {1,5,4}, {7,7,1}, {9,6,3}, {8,3,4}, {5,6,3}, {0,6,1}, {3,2,5}};
        private static int[][] gen2 = {{5,4,3}, {8,6,15}, {1,4,4}, {2,7,9}, {9,0,10}, {3,5,4}, {7,2,6}, {3,3,2}};

        private static int[][] invalidGen = {{4,6,3}, {3,2,-1}};



        private List<Map<String,Integer>> genToLstOfMap(int[][] g) {
            return Arrays.stream(g)
                    .map( arr -> IntStream.range(0,3).boxed().collect(Collectors.toMap(i -> i==0 ? "x" : i==1 ? "y" : "size", i -> arr[i]==-1 ? null:arr[i] )) )
                    .collect(Collectors.toList());
        }

        private List<List<Integer>> expArrToLofL(int[][] exp) {
            return Arrays.stream(exp)
                    .map( arr -> Arrays.stream(arr ).boxed().collect(Collectors.toList()) )
                    .collect(Collectors.toList());
        }



        @Test
        public void sampleTest1() {

            int[][] expArr0 = {{0,6,5}, {1,5,3}, {3,1,2}, {4,7,2}, {5,2,4}, {6,7,3}, {7,1,3}, {7,2,1}};
            int[][] expArr1 = {{1,5,5}, {2,6,3}, {4,2,2}, {5,6,2}, {5,7,3}, {6,1,4}, {7,2,4}};
            int[][] expArr2 = {{4,3,23}};


            Blobservation blobs = new Blobservation(8);
            blobs.populate(genToLstOfMap(gen0));

            blobs.move();
            checker(blobs, expArr0);

            blobs.move();
            checker(blobs, expArr1);

            blobs.move(1000);
            checker(blobs, expArr2);
        }



        @Test
        public void sampleTest2() {

            int[][] expArr0 = {{0,6,1},  {1,1,1},  {1,6,2}, {2,1,5}, {2,6,7}, {4,2,6},  {6,7,3}, {7,1,2}, {7,4,4},  {7,7,1}, {8,7,3}};
            int[][] expArr1 = {{0,6,7},  {1,5,3},  {2,2,6}, {4,1,6}, {6,1,2}, {6,4,4},  {6,6,7}};
            int[][] expArr2 = {{2,4,13}, {3,3,3},  {6,1,8}, {6,2,4}, {6,4,7}};
            int[][] expArr3 = {{1,4,4},  {2,4,13}, {2,7,9}, {3,3,5}, {3,5,4}, {5,4,3},  {6,1,8}, {6,2,4}, {6,4,7},  {7,2,6}, {8,6,15}, {9,0,10}};
            int[][] expArr4 = {{2,4,9},  {3,3,13}, {3,6,9}, {4,4,4}, {5,3,4}, {5,4,10}, {6,2,6}, {7,2,8}, {7,5,15}, {8,1,10}};
            int[][] expArr5 = {{4,3,22}, {5,3,28}, {5,4,9}, {6,2,29}};
            int[][] expArr6 = {{5,3,88}};

            Blobservation blobs = new Blobservation(10,8);

            blobs.populate(genToLstOfMap(gen1));
            blobs.move();
            checker(blobs, expArr0);
            blobs.move(2);
            checker(blobs, expArr1);
            blobs.move(2);
            checker(blobs, expArr2);

            blobs.populate(genToLstOfMap(gen2));
            checker(blobs, expArr3);
            blobs.move();
            checker(blobs, expArr4);
            blobs.move(3);
            checker(blobs, expArr5);

            assertFailMove(blobs, -3);

            blobs.move(30);
            checker(blobs, expArr6);

            assertFailPopulate(blobs, invalidGen);
        }


        private void checker(Blobservation blobs, int[][] expAsArr) {
            assertEquals(expArrToLofL(expAsArr), blobs.printState());
        }

        private void assertFailMove(Blobservation blobs, int m) {
            try {
                blobs.move(m);
                fail(String.format("Call of 'move(%d)' should have raised a RuntimeException", m));
            } catch (RuntimeException e) {
                assertTrue(true);
            }
        }

        private void assertFailPopulate(Blobservation blobs, int[][] gen) {
            try {
                blobs.populate(genToLstOfMap(gen));
                fail("Invalid elements should trigger a RuntimeException in 'populate'");
            } catch (RuntimeException e) {
                assertTrue(true);
            }
        }


}

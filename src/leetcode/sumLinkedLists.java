package leetcode;

import java.awt.image.PixelInterleavedSampleModel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class sumLinkedLists {
    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

            ListNode lres = new ListNode(0);
            ListNode ltemp = lres;
            List<ListNode>nodes = new ArrayList<>();
            Integer remainder = 0;

            while(l1 !=null || l2 !=null){
                Integer l1val = l1 == null ? 0:l1.val;
                Integer l2val = l2 == null ? 0:l2.val;

                Integer newVal = l1val + l2val + remainder;

                if(newVal > 9){
                    remainder = 1;
                    newVal = newVal %10;
                }
                else{
                    remainder = 0;
                }
                nodes.add(new ListNode(newVal));
                ltemp.val = newVal;
                ListNode newNode = new ListNode();
                ltemp.next = newNode;   // this changes the actual data of the object
                ltemp = ltemp.next;     // this changes the object ltemp is referncing

                l1 = l1 == null ? null:l1.next;
                l2 = l2 == null ? null:l2.next;
            }

            if(remainder>0){
                ltemp.val = remainder;
            }

            return lres;
        }

/**
 * The below does not work because the constraints of the problem include that the list can be up to 100 digits long, which is farrrr too big for an integer with max size 2^32
 * 2^32 = 2147483647 ~ 2.14*10^9
 * Hence instead method will need to add the numbers by digit
 *
 * Digits are in revser order which means we always start with lower OoM and go higher rigght?
 *ALgorithm:
 *  1. Iterate through both list nodes until both return null as next
 *  2. For each iteration, sum the two list node vals + any remainder, and track remainder.
 *  3. Create a new list node with val of this sum, and set it as the next from the last list node
 *  4. at end retrn the first list node
 *
 * if we could access the digits from front to back we could
 *  sum the two digits, if greater than 10 carry over and only use remainder as part of next digit addition
 *  1. Create two lists of the
  */


//        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//            double i_l1 = listToInt(l1);
//            double i_l2 = listToInt(l2);
//            return intToListNode(i_l1+i_l2);
//        }
//
//        public ListNode intToListNode(double i_l){
//            String i_l_str = String.valueOf(i_l);
//            List<ListNode> nodes = new ArrayList<>();
//            for(int i = i_l_str.length()-1;i>=0;i--){
//                //cycling from reverse order
//                nodes.add(new ListNode(Character.getNumericValue(i_l_str.charAt(i))));
//            }
//            for(int i = 0;i<i_l_str.length()-1;i++){
//                nodes.get(i).next = nodes.get(i+1);
//            }
//            return nodes.get(0);
//        }
//
//        public BigInteger listToInt(ListNode l1){
//            Integer digits = 0; //0 to refernce an index
//            List<Integer> digitList = new ArrayList<>();
//            ListNode  l = l1;
//
//            while(l != null){
//                digits ++;
//                digitList.add(l.val);
//                l = l.next;
//            }
//            //digitlist is a reverse ordered list  of numbers digits
//            double result = 0;
//            for(int i = digits; i>0; i--){
//                result = result + digitList.get(i-1) * Math.pow(10,(i-1));
//            }
//            return result;
//    }
}

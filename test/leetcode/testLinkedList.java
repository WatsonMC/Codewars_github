package leetcode;

import org.junit.Test;

public class testLinkedList {

    @Test
    public void testsimple(){
        sumLinkedLists.ListNode l1 = new sumLinkedLists.ListNode(9);

        sumLinkedLists sll = new sumLinkedLists();

        sumLinkedLists.ListNode ll1 = new sumLinkedLists.ListNode(9);
        sumLinkedLists.ListNode ll2= new sumLinkedLists.ListNode(9,ll1);
        sumLinkedLists.ListNode ll3= new sumLinkedLists.ListNode(9,ll2);
        sumLinkedLists.ListNode ll4= new sumLinkedLists.ListNode(9,ll3);
        sumLinkedLists.ListNode ll5= new sumLinkedLists.ListNode(9,ll4);
        sumLinkedLists.ListNode ll6= new sumLinkedLists.ListNode(9,ll5);
        sumLinkedLists.ListNode ll7= new sumLinkedLists.ListNode(9,ll6);
        sumLinkedLists.ListNode ll8= new sumLinkedLists.ListNode(9,ll7);
        sumLinkedLists.ListNode ll9= new sumLinkedLists.ListNode(9,ll8);
        sumLinkedLists.ListNode ll10= new sumLinkedLists.ListNode(1,ll9);

        sumLinkedLists.ListNode lres = sll.addTwoNumbers(ll10,l1);
        System.out.println(lres.val);

    }
}

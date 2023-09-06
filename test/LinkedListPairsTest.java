import code.LinkedListPairs;
import code.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListPairsTest {

    @Test
    public void basicTests() {
        executeTest(null, null);
        executeTest(new Node("A"), new Node("A"));
        executeTest(new ListBuilder().withValue("A").withValue("B").withValue("C").withValue("D").build(), new ListBuilder().withValue("B").withValue("A").withValue("D").withValue("C").build());
    }

    // use this to build your own tests
    private class ListBuilder {
        private Node head = null, last = null;

        public ListBuilder withValue(String value) {
            if (head == null) {
                head = new Node(value);
                last = head;
            } else {
                last.next = new Node(value);
                last = last.next;
            }
            return this;
        }

        public Node build() {
            return head;
        }
    }

    private static void executeTest(Node input, Node expectedResult) {
        Node output = LinkedListPairs.swapPairs(input);
        if (expectedResult == null) {
            assertNull(output);
            return;
        }

        final String expected = expectedResult.printList();
        final String actual = output.printList();
        assertEquals("The nodes are not in the correct order:\n", expected, actual);
    }

}
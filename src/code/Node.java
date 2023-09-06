package code;

public class Node {
    private String value;
    public Node next;

    public Node(String value) { this.value = value; }

    public Node(Node node) {
        this.value = node.value;
        this.next = node.next == null ? null : new Node(node.next);
    }

    public String getValue() { return value; }

    public String toString() { return this.value; }

    public String printList() {
        if (this.next == null) return this.toString() + " ->";
        return this.toString() + " -> " + next.printList();
    }
}
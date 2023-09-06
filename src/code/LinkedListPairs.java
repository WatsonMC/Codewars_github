package code;


import java.util.ArrayList;
import java.util.List;

public class LinkedListPairs {
    public static Node swapPairs(Node head) {
        if(head == null){ return null;}
        List<Node> nodes = new ArrayList<>();
        List<Node> swappedNodes = new ArrayList<>();
        Node currentNode = head;
        while(currentNode != null){
            nodes.add(new Node(currentNode));
            currentNode = currentNode.next;
        }
        for(int i = 0; i< nodes.size();i = i+2){
            if(nodes.size()-i >1) { //last element
                swappedNodes.add(nodes.get(i+1));
            }
            swappedNodes.add(nodes.get(i));
        }
        for(int i = 0; i< nodes.size();i++){
            if(i == nodes.size()-1){
                swappedNodes.get(i).next = null;
            }
            else{
                swappedNodes.get(i).next = swappedNodes.get(i+1);
            }
        }
        return swappedNodes.get(0);
    }

}

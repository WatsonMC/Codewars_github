package leetcode;

import com.sun.source.tree.Tree;

import java.util.*;

public class MinDiffBST {

    /**
     * This solution does not make use of two properties of a BST that I somehow have never realised before:
     *  1. The left lode of a tree is always less than the root node
     *  2. the right node of the dtree is always greater than the root node
     *  This is fundamentally why BST is so useful, and I have been compeltely oblivious to it.
     */

    public int getMinimumDifference(TreeNode root){
        TreeSet<Integer> sortedVals = new TreeSet<>();
        List<TreeNode> nodes = new ArrayList<>();
        int difference = Integer.MAX_VALUE;
        nodes.add(root);
        while (!nodes.isEmpty()){
            TreeNode currentNode = nodes.remove(0);
            int currentVal =currentNode.val;
            if(!sortedVals.add(currentVal)){return 0;}
            Integer higher = sortedVals.higher(currentVal);
            Integer lower = sortedVals.lower(currentVal);
            if(higher!=null && higher-currentVal <difference){
                difference = higher-currentVal;
            }
            if(lower!=null && currentVal-lower<difference){
                difference= currentVal-lower;
            }
            if(currentNode.left!=null){nodes.add(currentNode.left);}
            if(currentNode.right!=null){nodes.add(currentNode.right);}
        }
        return difference;
    }







    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) { this.val = val; }

        TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
        }
    }
}

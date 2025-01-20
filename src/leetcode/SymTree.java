package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class SymTree {


    public boolean isSymmetric2(TreeNode root){
        return isSymmetricRecursive(root.left,root.right);
    }

    public boolean isSymmetricRecursive(TreeNode left, TreeNode right){
        //base case is both are null and we are at end of tree
        if(left == null && right == null){
            return true;
        }
        //second base case = one is null, other is not
        else if((left == null && right !=null) || (right ==null && left !=null)){
            return false;
        }
        //recursive case, return the comparison of the two nodes && recurwsive call of left.left+right.right && recursive call of left.right+right.left
        else{
            return (
                    left.val == right.val &&
                    isSymmetricRecursive(left.left,right.right) &&
                    isSymmetricRecursive(left.right,right.left)
                    );
        }
    }

    public boolean isSymmetric(TreeNode root) {
        if(root.left == null && root.right == null){return true;}
        Queue<TreeNode> leftNodes = new LinkedList<>();
        Queue<TreeNode> rightNodes = new LinkedList<>();
        leftNodes.add(root.left);
        rightNodes.add(root.right);

        while(!leftNodes.isEmpty() || !rightNodes.isEmpty()){
            if(leftNodes.size() != rightNodes.size()){
                return false;
            }

            TreeNode leftNode = leftNodes.remove();
            TreeNode rightNode = rightNodes.remove();
            if((leftNode == null && rightNode !=null) || (rightNode == null && leftNode != null)){return false;}
            if(leftNode == rightNode || leftNode.val == rightNode.val){
                if(leftNode != null){
                    leftNodes.add(leftNode.left);
                    leftNodes.add(leftNode.right);
                    rightNodes.add(rightNode.right);
                    rightNodes.add(rightNode.left);
                }
            }else{
                return false;
            }
        }
        return true;
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

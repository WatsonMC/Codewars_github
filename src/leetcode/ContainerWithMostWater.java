package leetcode;

import java.util.*;

public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        /**
         * height:
         *  i = width
         *  height[i] = height
         *
         * Noting that Area of a rectangle is always HxW, we have to find the maximum area possible from the bars
         *
         * Brute Force:
         * 1. Cycle through each index i
         * 2. for each index i, check against every other index j and calculate the area
         * 3. if area_i > max, set max = area_i
         *
         * This would be O(n2) complex I believe, as for every index i we would need to iterate through the entire list again
         *
         * Smarter way
         * 1. Create a map with the heights and the indices where those heights are present stored as a list of integers, heights as  keys.
         * 2. From i=0 to n
         *
         */

        //create a map of each height, and the indeces of bars with those heights
        Map<Integer, List<Integer>> heightMap = new HashMap<>();
        for (int i = 0; i < height.length; i++) {
            if(!heightMap.containsKey(height[i])){
                heightMap.put(height[i], new ArrayList<>());
            }
            heightMap.get(height[i]).add(i);
        }


        Map<Integer, Integer> heightAreas = new HashMap<>();
        //iterate through heights calculating the left and right area, and adding the max to the corresponding map
        for (int h : height) {
            int leftArea =0;
            int rightArea=0;
            int rightIndex = Collections.max(heightMap.get(h));
            int leftIndex = Collections.min(heightMap.get(h));
            List<Integer> viableIndeces = getHeightIndexList(h, heightMap);
            leftArea = (Collections.max(viableIndeces) - leftIndex) * h;
            rightArea = (rightIndex - Collections.min(viableIndeces)) *h;
            heightAreas.put(h,Math.max(leftArea,rightArea));
        }
        return Collections.max(heightAreas.values());
    }

    //takes the heighMapList and the  height and returns all indeces of heights greater than or equal to that height as a single list
    public List<Integer> getHeightIndexList(Integer h, Map<Integer,List<Integer>> map){
        List<Integer> indexList= new ArrayList<>();
        for(Integer height: map.keySet()){
            if(height >= h){
                indexList.addAll(map.get(height));
            }
        }
        return indexList;
    }

    public int maxaArea2(int[] height){
        //create required variables
        int left =0;
        int right = height.length-1;
        int max = Integer.MIN_VALUE;

        while(left<right){
            int area = Math.min(height[left], height[right]) * (right-left);
            max = Math.max(max, area);
            if(height[left] <height[right] ){
                left++;
            }else{
                right--;
            }
        }
        return max;
    }
}


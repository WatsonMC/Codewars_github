package code;

import java.util.*;

public class ZooDisaster {

    public static Map<String, List<String>> zooberEats;
    static {
        Map<String, List<String>> myMap = new HashMap<>();
        myMap.put("antelope",Arrays.asList("grass"));
        myMap.put("big-fish",Arrays.asList("little-fish"));
        myMap.put("bug",Arrays.asList("leaves"));
        myMap.put("bear",Arrays.asList("big-fish","bug","chicken","cow","leaves","sheep"));
        myMap.put("chicken",Arrays.asList("bug"));
        myMap.put("cow",Arrays.asList("grass"));
        myMap.put("fox",Arrays.asList("chicken","sheep"));
        myMap.put("giraffe",Arrays.asList("leaves"));
        myMap.put("lion",Arrays.asList("antelope", "cow"));
        myMap.put("panda",Arrays.asList("leaves"));
        myMap.put("sheep",Arrays.asList("grass"));
        zooberEats = myMap;
    }
    //animals only eat animals left and right of them
    //the left most animal will always eat before any other animals. So start left, work through
    // may be some animals not listed who are inedible, and cannot be eaten

    /**
     *
     * @param zoo
     * @return  List of strings with first string bein input string, last string being final zoo state, middle elements being the order of who ate who.
     */
    public static String[] whoEatsWho(final String zoo) {
        // Your code here
        //turn zoo into list
        List<String> zooList = new ArrayList<>();
        zooList.addAll(Arrays.asList(zoo.split(",")));
        List<String> eats = new ArrayList<>();

        //Starting from index 0, check if there are edible animals left, then right. If any are found, start again from
        int i =0;
        while(i< zooList.size()){
            boolean hungry = true;
            String animal = zooList.get(i);
            if(!(i==0)){
               if(zooberEats.getOrDefault(animal,new ArrayList<>()).contains(zooList.get(i-1))){
                   eats.add(animal + " eats " +zooList.get(i-1) );
                   zooList.remove(i-1);
                   i = Math.max(i-2, 0)-1;
                   hungry =false;
               }
            }

            if(!(i==zooList.size()-1) && hungry){
                if(zooberEats.getOrDefault(animal, new ArrayList<>()).contains(zooList.get(i+1))){
                    eats.add(animal + " eats " +zooList.get(i+1) );
                    zooList.remove(i+1);
                    i--;
                }
            }
            i++;
        }
        String[] result= new String[2+eats.size()];
        result[0] = zoo;
        for(i=0; i<eats.size();i++){
            result[i+1] = eats.get(i);
        }
        result[result.length-1]  = String.join(",",zooList);
        return result;
        /**
         * For animal in animalList, from first to last
         *  if(!first animal)
         *      if (animal can eat animal left of it)
         *          remove eaten animal         -> animal i becomes animal i-1
         *          add note to tracker
         *          set index to Max(currentIndex-2, 0)-1     -> for index 1, we may eat index 0, and want to reset to 0. For index 2 we may eat index 1 and want to reset to index 0 too
         *          next i
         *
         *  if(!Last animal)
         *      if(animal can eat animal right of it)
         *          renmove eaten animal -> animal stays as i
         *          add to tracker
         *          set index to i-1
         */
    }
}




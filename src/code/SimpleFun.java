package code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleFun {

    public static int[] distributionOf(int[] golds) {
        List<Integer> goldList =  new ArrayList<>();
        IntStream.of(golds).forEach(x -> goldList.add(x));
        int[] result =  new int[]{0, 0};
        int progIndex =1;
        while(!goldList.isEmpty()){
            progIndex = progIndex ==0?1:0;
            boolean head = goldList.get(0)>= goldList.get(goldList.size()-1);
            if(head){
                result[progIndex] = result[progIndex]+goldList.remove(0);
            }
            else{
                result[progIndex] = result[progIndex]+goldList.remove(goldList.size()-1);
            }
        }
        return result;
    }
}

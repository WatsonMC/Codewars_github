package code;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WeightForWeight {
    public static String orderWeight(String str) {
        /**
         * take in weights as strings, split into list of weights
         * For each weight create a
         */
        List<String> originalWeights = Arrays.asList(str.split((" ")));
        Collections.sort(originalWeights, new WeightComparator());
        return originalWeights.stream().collect(Collectors.joining(" "));
}
    static class WeightComparator implements Comparator<String>{
        @Override
        public int compare(String s, String t1) {
            Integer sValue = s.chars()
                    .mapToObj(c -> Character.getNumericValue((char) c))
                    .reduce(0,Integer::sum);
            Integer t1Value  = t1.chars()
                    .mapToObj(c -> Character.getNumericValue((char) c))
                    .reduce(0,Integer::sum);
            if(s.equals(t1)){
                return 0;
            }
            if(sValue == t1Value){
                return s.compareTo(t1);
            }
            else if(sValue<t1Value){
                return 1;
            }
            else{
               return -1;
            }
        }
    }



}

package code;

import java.util.ArrayList;
import java.util.List;

public class EgyptianFractions {
    public static String decompose(String nrStr, String drStr) {
        Integer n = Integer.parseInt(nrStr);
        Integer d = Integer.parseInt(drStr);
        float  remainder = (float)n/(float)d;
        if(n == 0){return "[]";}  //if zero

        List<String> resultList = new ArrayList<>();
        if(n>d){
            //greater than 0 value
            resultList.add(String.valueOf(n/d));    //remove >1
            remainder -= Math.floor(n/d);
        }

        long firstDenom =  Math.max(2, (Math.round(1.0/remainder)));
        firstDenom = n/d == 1/2 ? 4: firstDenom;

        resultList = decomposeRec(resultList,remainder,firstDenom);
        return "[" +String.join(", ", resultList)+"]";
    }

    public static List<String> decomposeRec(List<String> seq, float remainder, long nextDenom){
        //base case
        if(remainder  <= 0){
            return seq;
        }
        //rec case
        else{
            seq.add("1/"+String.valueOf(nextDenom));
            remainder -= (1.0/(float)nextDenom);
            nextDenom = ((Math.round(1.0/remainder))==nextDenom ? nextDenom+1:  Math.round(1.0/remainder));
            return decomposeRec(seq,remainder,nextDenom);
        }
    }

}

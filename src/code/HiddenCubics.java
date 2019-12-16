package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HiddenCubics {
    public String isSumOfCubes(String s) {
        List<String> digits = Arrays.asList(
                Stream.of(s.split(""))
                .map(a-> {
                    if(!Character.isDigit(a.charAt(0))){    //replace non digit chars with spaces
                        return " ";}else{ return a;}
                })
                .collect(Collectors.joining("")) //digits seperated by spaces,
                .trim()
                .split("\\s+")); //split out on multiple spaces
        if(digits.size() == 0||digits.get(0).length()==0){return "Unlucky";}

        List<String> hiddenCubics = digits.stream()
                .map(a ->{
                    if(a.length()>3){
                        List<String> subList = new ArrayList();
                        for(int index = 0; index+3<a.length();index+=3){
                            subList.add(a.substring(index, index+3));
                        }
                        return subList;
                    }
                    return Arrays.asList(a);
                })
                .flatMap(List::stream)
                .filter(a-> checkNumber(a))
                .collect(Collectors.toList());
        if(hiddenCubics.size() == 0||hiddenCubics.get(0).length()==0){return "Unlucky";}

        Integer sum = hiddenCubics.stream().map(a -> Integer.valueOf(a))
                .reduce(0,Integer::sum);
        StringBuilder result = new StringBuilder();
        for(String number: hiddenCubics){
            result.append(number + " ");
        }
        return result.append(sum.toString() + " Lucky").toString();
    }


    public boolean checkNumber(String digits){
        if(
                Stream.of(digits.split(""))
                .map(a -> (int)Math.pow(Integer.valueOf(a),3))
                .reduce((a,b) -> a+b).get()
                        .equals(Integer.valueOf(digits))
        ){
            return true;
        }
        return false;
    }

}

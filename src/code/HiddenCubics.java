package code;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HiddenCubics {
    public String isSumOfCubes(String s) {
        List<String> digits = Stream.of(s.split(""))
                .filter(a-> Character.isDigit(a.charAt(0)))
                .collect(Collectors.toList());
        List<Integer> numbers = IntStream.range(0, digits.size()/3)
                .mapToObj(i -> digits.subList(i*3, (Math.min(3*(i+1),digits.size()))))
                .map(i -> Integer.valueOf(i.stream().collect(Collectors.joining(""))))
                .collect(Collectors.toList());
        return numbers.stream()
                .filter(a -> checkNumber(a)).reduce(0,Integer::sum).toString();
        //didnt read question properly
    }

    public boolean checkNumber(Integer number){
        if(
                Stream.of(String.valueOf(number).split(""))
                .map(a -> Integer.valueOf(a))
                .reduce(0, (a,b) -> a + b^3) == number
        ){
            return true;
        }
        return false;
    }

}

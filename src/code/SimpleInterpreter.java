package code;

import java.util.HashMap;
import java.util.Map;

public class SimpleInterpreter {
public static String movRegex = "mov";
public static String incRegex = "inc";
public static String decRegex = "dec";
public static String jumpRegex = "dec";
public static String regRegex = "[a-zA-Z]+";
public static String valueRegex = "\\-{0,1}[0-9]+";

        Map<String,Integer> registers = new HashMap<>();

        for(int pointer= 0; pointer<program.length;pointer++){
            String[] instructions = program[pointer].split(" ");
            if(instructions[0].matches(movRegex)){
                Integer newRegValue = instructions[2].matches(valueRegex) ? Integer.valueOf(instructions[2]) : registers.get(instructions[1]);
                registers.put(instructions[1],newRegValue);
            }
            if
            //inc

            //dec
            //mov
                //mov value
                //mov reg
            //jump
                //x !=0
                //x=0
        }

        return null;
    }


}

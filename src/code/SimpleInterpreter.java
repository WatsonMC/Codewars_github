package code;

import java.util.HashMap;
import java.util.Map;

public class SimpleInterpreter {
public static String movRegex = "mov";
public static String incRegex = "inc";
public static String decRegex = "dec";
public static String jumpRegex = "jnz";
public static String regRegex = "[a-zA-Z]+";
public static String valueRegex = "\\-{0,1}[0-9]+";


    public static Map<String, Integer> interpret(String[] program){
        Map<String,Integer> registers = new HashMap<>();

        for(int pointer= 0; pointer<program.length;pointer++){
            String[] instructions = program[pointer].split(" ");
            if(instructions[0].matches(movRegex)){
                Integer newRegValue = instructions[2].matches(valueRegex) ? Integer.valueOf(instructions[2]) : registers.get(instructions[2]);
                registers.put(instructions[1],newRegValue);
            }
            if(instructions[0].matches(incRegex)){
                registers.put(instructions[1], registers.get(instructions[1])+1);
            }
            if(instructions[0].matches(decRegex)){
                registers.put(instructions[1], registers.get(instructions[1])-1);
            }
            if(instructions[0].matches(jumpRegex)){
                Integer xValue = instructions[1].matches(valueRegex) ? Integer.valueOf(instructions[1]) :registers.get(instructions[1]);
                Integer yValue = instructions[2].matches(valueRegex) ? Integer.valueOf(instructions[2]) :registers.get(instructions[2]);
                if(xValue !=0){ pointer += yValue -1;}
            }
        }
        return registers;
    }


}

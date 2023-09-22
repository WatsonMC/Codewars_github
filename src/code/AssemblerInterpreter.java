package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblerInterpreter {

    private static class Environment{
        private static Map<String,Integer> registers = new HashMap<>();
        private List<Command> commands = new ArrayList<>();
        private static Integer pointer = 0;

        public static void setRegister(String register, Integer value){
            registers.put(register,value);
        }

        public static Integer getRegister(String register){
            return registers.get(register);
        }

        public static Integer getRegOrValue(String str){
            Integer setValue;
            if(!str.matches("-?\\d+")){
                setValue = Environment.getRegister(str);
            }else{
                setValue = Integer.parseInt(str);
            }
            return setValue;
        }

    }

    private static abstract class Command{
        String arg1;
        Command(String arg1){
            this.arg1 = arg1;
        }
        public abstract void call();
    }

    private static class DivCommand extends Command{
        String arg2;
        DivCommand(String arg1, String arg2){
            super(arg1);
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1)/Environment.getRegOrValue(arg2);
        }
    }
    private static class MulCommand extends Command{
        String arg2;
        MulCommand(String arg1, String arg2){
            super(arg1);
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1)*Environment.getRegOrValue(arg2);
        }
    }
    private static class IncCommand extends Command{
        IncCommand(String arg1){
            super(arg1);
        }

        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1)+1;
            Environment.setRegister(arg1,setValue);
        }
    }
    private static class DecCommand extends Command{
        DecCommand(String arg1){
            super(arg1);
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1)-1;
            Environment.setRegister(arg1,setValue);
        }
    }
    private static class SubtractCommand extends Command{
        String arg2;
        SubtractCommand(String arg1, String arg2){
            super(arg1);
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1) - Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
        }
    }
    private static class AddCommand extends Command{
        String arg2;
        AddCommand(String arg1, String arg2){
            super(arg1);
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1) + Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
        }
    }
    private static class MovCommand extends Command{
        private String arg2;
        MovCommand(String arg1, String arg2){
            super(arg1);
            this.arg2 = arg2;
        }
        @Override
        public void call() {
            Environment.setRegister(arg1, Environment.getRegOrValue(arg2));
        }
    }



}

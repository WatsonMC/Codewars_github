package code;

import java.util.*;

public class AssemblerInterpreter {

    private static class Environment{
        private static Map<String,Integer> registers = new HashMap<>();
        private static Map<String,Integer> labels = new HashMap<>();

        private static Stack<Integer> callStack = new Stack<>();

        private List<Command> commands = new ArrayList<>();
        private static Integer pointer = 0;

        private static Integer lastCompare;

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

        public static void setLabelPointer(String label, Integer pointer){
            labels.put(label,pointer);
        }
        public static Integer getLabelPointer(String label){
            return labels.get(label);
        }

        public static void setPointer(Integer pnt){
            pointer = pnt;
        }
        public static void setCompare(Integer val){
            lastCompare = val;
        }
        public static Integer getCompare(){
            return lastCompare;
        }

        public static void addToStack(Integer pointer){
            callStack.add(pointer);
        }

        public static Integer removeFromStack(){
            return callStack.pop()
        }

    }

    private static abstract class Command{
        String arg1;
        Command(String arg1){
            this.arg1 = arg1;
        }
        public abstract void call();
    }
    //JUMP COMMANDS
    public static class JmpCommand extends Command{
        String label;
        JmpCommand(String arg1, String label){
            super(arg1);
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            Environment.setPointer(jmpPointer);
        }
    }

    //ARITH COMMANDS
    public static class CmpCommand extends Command{

        String arg2;
        CmpCommand(String arg1, String arg2){
            super(arg1);
            this.arg2  = arg2;
        }

        @Override
        public void call(){
            //arg1> arg2 = 1
            //arg1<arg2 = -1
            //arg1 == arg2 = 0
            Integer i_arg1 = Environment.getRegOrValue(arg1);
            Integer i_arg2 = Environment.getRegOrValue(arg2);
            Integer compareVal = i_arg2 == i_arg2 ? 0: (i_arg1 <i_arg2 ? -1:1);
            Environment.setCompare(compareVal);
        }
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

    //PROC COMMANDS
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

    private static class LblCommand extends Command{
        Integer pointer;
        LblCommand(String arg1, Integer pointer){
            super(arg1);
            this.pointer = pointer;
            call();
        }

        @Override
        public void call(){
            Environment.setLabelPointer(arg1,pointer);
        }
    }

    private static class RetCommand extends Command{
        RetCommand(String arg1){
            super(arg1);    //unneeded, prefer composition over inheritence here...
        }

        @Override
        public void call(){
            Environment.setPointer(Environment.removeFromStack());
        }
    }

    public static class CallCommand extends Command{
        Integer pointer;
        CallCommand(String arg1, Integer pointer){
            super(arg1);
            this.pointer = pointer;
        }

        @Override
        public void call(){
            Environment.addToStack(this.pointer);
            Environment.setPointer(Environment.getLabelPointer(arg1));
        }
    }

}

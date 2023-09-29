package code;

import java.util.*;

public class AssemblerInterpreter {
    //Extended from CalculusBear's solution to 'Simple Assembler Interpeter'
    public static String interpret(final String input) {
        // Your code here!
        Environment.clear();
        int pointer = 0;
        for(String commandString: input.split("\n")){
            if(commandString.length()>0 && commandString.indexOf(";")!=0){
                Environment.addCommand(CommandFactory.createCommand(commandString,pointer));
                pointer++;
            }
        }
        return Environment.eval();
    }
    public static class Environment{
        private static Map<String,Integer> registers = new HashMap<>();
        private static Map<String,Integer> labels = new HashMap<>();
        private static Stack<Integer> callStack = new Stack<>();
        private static List<Command> commands = new ArrayList<>();
        private static Integer pointer = 0;
        private static Integer lastCompare;
        private static String output = null;


        public static String eval(){
            while(pointer >=0){
                // testing simple and got to point where pointer == null after trying to call function. It will be something to do with
                // the map key being incorrect somehow
                if(pointer>=commands.size()){return null;}
                commands.get(pointer).call();
            }
            return output;
        }

        public static void clear(){
            registers = new HashMap<>();
            labels = new HashMap<>();
            callStack = new Stack<>();
            commands = new ArrayList<>();
            output = null;
            pointer = 0;
        }

        public static void addCommand(Command cmd){commands.add(cmd);}

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


        public static void incrementPointer(){pointer++;        }
        public static void setPointer(Integer pnt){
            pointer = pnt;
        }
        public static Integer getPointer(){
            return pointer;
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
            return callStack.pop();
        }

        public static void setOutput(String arg){
            output = arg; //this is dodgey as we are using enviroment to set athe static interpreter classes output
        }

    }

    private static abstract class Command{
        public abstract void call();
    }
    //JUMP COMMANDS rest of them
    public static class JmpCommand extends Command{
        String label;
        JmpCommand( String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            Environment.setPointer(jmpPointer);
        }
    }
    public static class JgCommand extends Command{
        String label;
        JgCommand( String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()==1){
                Environment.setPointer(jmpPointer);
            }
            else{
                Environment.incrementPointer();
            }
        }
    }
    public static class JgeCommand extends Command{
        String label;
        JgeCommand( String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()>=0){
                Environment.setPointer(jmpPointer);
            }
        }
    }
    public static class JlCommand extends Command{
        String label;
        JlCommand(String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()==-1){
                Environment.setPointer(jmpPointer);
            }
            else{
                Environment.incrementPointer();
            }
        }
    }
    public static class JleCommand extends Command{
        String label;
        JleCommand(String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()<=0){
                Environment.setPointer(jmpPointer);
            }
            else{
                Environment.incrementPointer();
            }
        }
    }
    public static class JeCommand extends Command{
        String label;
        JeCommand( String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()==0){
                Environment.setPointer(jmpPointer);
            }
            else{
                Environment.incrementPointer();
            }
        }
    }
    public static class JneCommand extends Command{
        String label;
        JneCommand( String label){
            this.label =label;
        }
        @Override
        public void call(){
            Integer jmpPointer = Environment.getLabelPointer(label);
            if(Environment.getCompare()!=0){
                Environment.setPointer(jmpPointer);
            }
            else{
                Environment.incrementPointer();
            }
        }
    }

    //ARITH COMMANDS
    public static class CmpCommand extends Command{
        String arg1;
        String arg2;
        CmpCommand(String arg1, String arg2){
            this.arg1 =arg1;
            this.arg2  = arg2;
        }

        @Override
        public void call(){
            //arg1> arg2 = 1
            //arg1<arg2 = -1
            //arg1 == arg2 = 0
            Integer i_arg1 = Environment.getRegOrValue(arg1);
            Integer i_arg2 = Environment.getRegOrValue(arg2);
            Integer compareVal = i_arg1 == i_arg2 ? 0: (i_arg1 <i_arg2 ? -1:1);
            Environment.setCompare(compareVal);
            Environment.incrementPointer();
        }
    }
    private static class DivCommand extends Command{
        String arg1;
        String arg2;
        DivCommand(String arg1, String arg2){
            this.arg1 = arg1;
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1)/Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
            Environment.incrementPointer();
        }
    }
    private static class MulCommand extends Command{
        String arg1;
        String arg2;
        MulCommand(String arg1, String arg2){
            this.arg1  =arg1;
            this.arg2 = arg2;
        }
        @Override
        public void call(){

            Integer setValue = Environment.getRegister(arg1)*Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
            Environment.incrementPointer();
        }
    }
    private static class SubtractCommand extends Command{
        String arg1;
        String arg2;
        SubtractCommand(String arg1, String arg2){
            this.arg1  =arg1;
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1) - Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
            Environment.incrementPointer();
        }
    }
    private static class AddCommand extends Command{
        String arg2;
        String arg1;
        AddCommand(String arg1, String arg2){
            this.arg1  =arg1;
            this.arg2 = arg2;
        }
        @Override
        public void call(){
            Integer setValue = Environment.getRegister(arg1) + Environment.getRegOrValue(arg2);
            Environment.setRegister(arg1,setValue);
            Environment.incrementPointer();
        }
    }

    //PROC COMMANDS
    private static class MovCommand extends Command{
        private String arg2;
        String arg1;
        MovCommand(String arg1, String arg2){
            this.arg1  =arg1;
            this.arg2 = arg2;
        }
        @Override
        public void call() {

            Environment.setRegister(arg1, Environment.getRegOrValue(arg2));
            Environment.incrementPointer();
        }
    }
    private static class LblCommand extends Command{
        String arg1;
        Integer pointer;
        LblCommand(String arg1, Integer pointer){
            this.arg1  =arg1;
            this.pointer = pointer;
            Environment.setLabelPointer(arg1,pointer);

        }

        @Override
        public void call(){
            Environment.incrementPointer();
        }
    }
    private static class RetCommand extends Command{
        @Override
        public void call(){
            Environment.setPointer(Environment.removeFromStack());
        }
    }
    public static class CallCommand extends Command{
        Integer pointer;
        String arg1;
        CallCommand(String arg1, Integer pointer){
            this.arg1  =arg1;
            this.pointer = pointer;
        }

        @Override
        public void call(){
            Environment.addToStack(this.pointer+1); //need to return to line after call

            Environment.setPointer(Environment.getLabelPointer(arg1));
        }
    }
    public static class MsgCommand extends Command{
        List<String> outputElements;
        public MsgCommand(String arg1){
            System.out.println(arg1);
            //arg1 is an infnite length string starting with 'msg' and ending with "," delmited elements
            outputElements = new ArrayList<>();
            String argLessCommand = arg1.trim().substring(4).trim();
            for(int i = 0; i<argLessCommand.length();i++){
                if(argLessCommand.substring(i,i+1).equals("'")){
                    int stringEndIndex = argLessCommand.substring(i+1).indexOf("'") +i+1; //we started offset by 1
                    outputElements.add(argLessCommand.substring(i,stringEndIndex+1));//save enclosing braces to differntiate regex vs string
                    i = stringEndIndex+1;
                }
                else if (argLessCommand.substring(i,i+1).matches("[a-zA-Z]")){
                    outputElements.add(argLessCommand.substring(i,i+1));

                }
            }
        }

        @Override
        public void call() {
            StringBuilder result = new StringBuilder();
            for(String element: outputElements){
                if(element.length() == 1){
                    result.append(String.valueOf(Environment.getRegOrValue(element)));
                }
                else{
                    result.append(element.substring(1,element.length()-1));
                }
            }
            Environment.setOutput(result.toString());
            Environment.incrementPointer();
        }
    }
    private static class EndCommand extends Command{
        @Override
        public void call(){
            Environment.setPointer(-1);
        }
    }



    //end command
    //msg command
    private static class CommandFactory{
        /**
         * Command types
         * 2 input
         * - mov done
         * - add done
         * - sub done
         * - div done
         * - mul done
         * - cmp done
         *
         * 1 input
         * - jmps
         * - call Done
         * - dec done
         * - inc done
         *
         * Pointer input
         * - label done
         *
         * Infinite input
         * - msg
         *
         * no inputs
         * ret done
         * end
         * comment
         *
         *
         */
        public static Command createCommand(String commandString, Integer pointer){
            if(commandString.indexOf(";")!=-1){
                commandString = commandString.split(";")[0];
            }
            String commandType = commandString.trim().split(" ")[0];
            String[] commandWithArgs = commandString.trim().split("\\s+");
            Command command;
            switch (commandType){
                case "mov":
                    command =  new MovCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case  "inc":
                    command = new AddCommand(commandWithArgs[1],"1");
                    break;
                case "dec":
                    command = new SubtractCommand(commandWithArgs[1], "1");
                    break;
                case  "add":
                    command = new AddCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case "sub":
                    command = new SubtractCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case "mul":
                    command = new MulCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case "div":
                    command = new DivCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case "cmp":
                    command = new CmpCommand(commandWithArgs[1].replaceAll(",",""),commandWithArgs[2]);
                    break;
                case "call":
                    command = new CallCommand(commandWithArgs[1],pointer);
                    break;
                case "ret":
                    command = new RetCommand();
                    break;
                case "end":
                    command = new EndCommand();
                    break;
                case "msg":
                    command = new MsgCommand(commandString);
                    break;
                case "jmp":
                    command = new JmpCommand(commandWithArgs[1]);
                    break;
                case "jl":
                    command = new JlCommand(commandWithArgs[1]);
                    break;
                case "jle":
                    command = new JleCommand(commandWithArgs[1]);
                    break;
                case "jg":
                    command = new JgCommand(commandWithArgs[1]);
                    break;
                case "jge":
                    command = new JgeCommand(commandWithArgs[1]);
                    break;
                case "je":
                    command = new JeCommand(commandWithArgs[1]);
                    break;
                case "jne":
                    command = new JneCommand(commandWithArgs[1]);
                    break;
                default:    //label, can be anything except above
                    command = new LblCommand(commandString.replaceAll(":",""), pointer);
            }
            return command;
        }
    }


}

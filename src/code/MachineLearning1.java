package code;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MachineLearning1 {
    //##########SITE
    //https://www.codewars.com/kata/5695995cc26a1e90fe00004d/train/java


    //Will always get a command call followed by a response call
    // The command call will be to set the command:Action settings
    // The response call will be to assert whether action was correct
    // So after each command, we have to stage the command/action pairing and check when response is called if it sould be valud
    public static List<Function<Integer, Integer>> _actions = Arrays.asList(x->x+1, x->0, x->x%2, x->x/2, x->x*100);
    public  Map<Integer,Integer> commandMap = new HashMap<>();    //map of commands to action numbers
    public  Map<Integer,CommandAttempt> testedCommands = new HashMap<>();   //map of command: (action:count of true res)
//    public static Integer currentCommand;
//    public static Integer currentAction;
    public CommandAttempt currentCommandAttempt;
    public int command(int cmd, int num){
        if(commandMap.keySet().contains(cmd)){
            currentCommandAttempt = testedCommands.get(cmd);
            return _actions.get(commandMap.get(cmd)).apply(num);}
        else{
            int cmdNum;
            if(!testedCommands.keySet().contains(cmd)){
                testedCommands.put(cmd, new CommandAttempt(cmd));
            }
            currentCommandAttempt = testedCommands.get(cmd);
//            testedCommands.put(cmd, cmdNum);
//            if(cmdNum >=5){System.out.println("Somehow have no valid command");}
//            currentAction = cmdNum;
//            currentCommand = cmd;
            return _actions.get(currentCommandAttempt.getAction()).apply(num);
        }

    }

    public void response(boolean response){
        if(currentCommandAttempt == null){
            System.out.println("Null command");
        }
        else{ System.out.println("cmd is: " + currentCommandAttempt.cmd + ", action is: " + currentCommandAttempt.currentAction + ", response is: " + response);}
        if(response && !commandMap.keySet().contains(currentCommandAttempt.cmd)){
            if(currentCommandAttempt.succeedAction()){
                commandMap.put(currentCommandAttempt.cmd, currentCommandAttempt.currentAction);
            }
        } else {
            currentCommandAttempt.failedAction(); // false, next action
        }
        currentCommandAttempt = null;
    }

    class CommandAttempt{
        public int cmd;
        public int currentAction =0 ; //Current action being tried, always starts at 0
        public Map<Integer, Integer> actionMemory;  //Map of actions to a count of their successful responses
        public int getAction(){
            return currentAction;
        }
        public CommandAttempt(int cmd){
            this.cmd = cmd;
            this.actionMemory = new HashMap<>(); //initialise map of action nums and attempts
            actionMemory.put(0,0);
            actionMemory.put(1,0);
            actionMemory.put(2,0);
            actionMemory.put(3,0);
            actionMemory.put(4,0);

        }
        public void failedAction(){
            this.currentAction = this.currentAction+1;
        }

        public boolean succeedAction(){
            this.actionMemory.put(currentAction, this.actionMemory.get(currentAction)+1);
            return(this.actionMemory.get(currentAction) >=2);
        }

    }


}

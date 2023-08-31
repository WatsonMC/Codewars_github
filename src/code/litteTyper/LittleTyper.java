package code.litteTyper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LittleTyper {


    public static String inferType(String context, String expression){

        return "";

    }

    /**
     * Helper to remove extra parentheses
     * Any parentheses wrapping just a single item
     *  Any parentheses wrapping just other parentheses
     *
     * @param input
     *  always has
     * @return
     */
    public String cleanParentheses(String input){
        return "";
    }

    /**
     * Helper to remove extra spaces. Extra space is any more than one in a row
     * Ensures any bracket is seperated by a space on its inside
     * @param input
     * @return
     */
    public static String cleanSpaces(String input){
        String multiSpaceRegex = "[ ]{2,}";
        int offset = 0;
        String leftBracketEdge =  "(?=(\\(([a-zA-Z0-9]|\\)|\\())).";
        String rightBracketEdge = "(?=(([a-zA-Z0-9]|\\))\\))).";
        String bracketMerge = "\\)\\(";
        Matcher matcher = Pattern.compile(leftBracketEdge).matcher(input);
        String tempString = input;
        while(matcher.find()){
            tempString = tempString.substring(0,matcher.start()+offset) + "( " + tempString.substring(matcher.start()+offset+1);
            offset ++;
        }
        offset = 0;
        input = tempString; // offset is variable by char index if pattern matches against original input. I.e no offset at index 0
        matcher =Pattern.compile(rightBracketEdge).matcher(input);
        while (matcher.find()){
            tempString = tempString.substring(0,matcher.start()+offset+1) + " )" + tempString.substring(matcher.start()+offset+2);
            offset ++;
        }
        offset = 0;
        input = tempString; // offset is variable by char index if pattern matches against original input. I.e no offset at index 0
        matcher =Pattern.compile(bracketMerge).matcher(input);
        while (matcher.find()){
            tempString = tempString.substring(0,matcher.start()+offset) + ") (" + tempString.substring(matcher.start()+offset+2);
            offset ++;
        }
        return tempString.replaceAll(multiSpaceRegex, " ");
    }


    class Function{

        private String name;
        public Function(String name){
            this.name = name;
        }

    }

}

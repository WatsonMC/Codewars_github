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
     * @param input cleaned through cleaned spaces.
     *  always has
     * @return
     */
    public static String cleanParentheses(String input){
//        input = cleanSpaces(input);
        //Question -> do we have to take append ( List -> List ) into append List -> List ?     In tbis case might need to also clean any
        //times whenre brackets surroun only types

        //simple mode
        String tempString = input;

        String singleBracketReg = "\\(\\s[a-zA-Z0-9]+\\s\\)";
        boolean noChange = true;
        while (noChange){
            tempString = input;
            Matcher matcher = Pattern.compile(singleBracketReg).matcher(input);
            while (matcher.find()){
                //matcher,start now contains start ], matcher

                tempString = input.substring(0,matcher.start()) + " "
                        + input.substring(matcher.start() + 1,matcher.end()-1) + " ";
                if(matcher.end() != input.length()){
                    tempString = tempString+ input.substring(matcher.end() );
                }

            }
            tempString= cleanSpaces(tempString.replaceAll("\\(\\s\\)",""));
            noChange = tempString !=input;
            input = tempString;
            System.out.print(input + "\n");
        }
        return tempString;



//        int eleCount=0;
//        int depth = 0;
//        for(String element:input.split(" ")){
//            if(element.equals("(")){
//                depth ++;
//                eleCount = 0;
//            } else if(element.equals(")")){
//                if(eleCount == 1){
//                    //single word
//                }
//            }
//        }
    }

    /**
     * Helper to remove extra spaces. Extra space is any more than one in a row
     * Ensures any bracket is seperated by a space on its inside
     * @param input
     * @return
     */
    public static String cleanSpaces(String input){
        String multiSpaceRegex = "[ ]{2,}";
        String leftBracketEdge =  "(?=(\\(([a-zA-Z0-9]|\\)|\\())).";
        String rightBracketEdge = "(?=(([a-zA-Z0-9]|\\))\\))).";
        String bracketMerge = "\\)\\(";
        String arrowLeft = "(?=([a-zA-Z0-9]\\-)).";
        String arrowRight = "(?=(\\>[a-zA-Z0-9])).";
        String scLeft = "(?=([a-zA-Z0-9]\\:)).";
        String scRight = "(?=(\\:([a-zA-Z0-9]|\\())).";
        String tempString = insertByRegex(leftBracketEdge,input,"( ",0);
        tempString = insertByRegex(rightBracketEdge,tempString," )", 1);
        tempString = insertByRegex(bracketMerge,tempString,") (", 0);
        tempString = insertByRegex(arrowLeft,tempString, " -",1);
        tempString = insertByRegex(arrowRight,tempString, "> ",0);
        tempString = insertByRegex(scLeft,tempString, " :",1);
        tempString = insertByRegex(scRight,tempString, ": ",0);
        return tempString.replaceAll(multiSpaceRegex, " ").trim()   ;
    }

    /**
     * Replaces a character based on regex match positions with a passed string
     * @param regex -> the regex to be matched
     * @param input -> the input string to be matched against
     * @param insertText -> the text to replace the character at matcher find location and insert offset
     * @param insertOffset -> offset from match index that char to replace is at (e.g. if macthing [a-z]\\( to replace  \\( offset = 1
     * @return String with insertion replacements completed
     */
    public static String insertByRegex(String regex, String input, String insertText, int insertOffset){
        //Start Match Index = matcher.start +insertOffset +offset
        String tempString = input;
        Matcher matcher = Pattern.compile(regex).matcher(input);
        int offset=0;
        int offsetIncrement = insertText.length()-1;
        while(matcher.find()){
            tempString = tempString.substring(0,matcher.start()+offset + insertOffset)
                    + insertText
                    + tempString.substring(matcher.start()+offset+offsetIncrement + insertOffset);
            offset += offsetIncrement;
        }
        return tempString;
    }


    class Function{

        private String name;
        public Function(String name){
            this.name = name;
        }

    }

}

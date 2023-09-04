package code.litteTyper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LittleTyper {


    public static String inferType(String context, String expression){
        // Take comtext, generate an abstraction of functions whihc we ca
            // cycle through
            // check if they match what exists in the expression
            //
        // Expression Processing
            // Clear braces
            // replace with singles if possible
            // clear braces
            // match functions
            // loop until match functions fails to change input


        // take context and split to lines
        // store each line as a function with types and name
        //Apply simplifications in form of cleaning functions



        //should have a list of functions aginast which to apply

        //string expression is then evaulated
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
        //TODO clean parantheses add in currying. Might have to make this a tree parser..

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


    public static class Function{
        /**
         * Functions can be:
         *  Function  = A

         *  Function = A -> B -> C
         *      == A,B ->C
         *      == A ->B,C
         *
         *  Function = (A -> B) -> A
         *  Function = (A->B) -> (A->B)
         *  Function = (A-B) - ((A_B) - (A-B))
         */


        private String name;
        private int arguments;
        private List<String> types = new ArrayList<>();
        public Function(String funcString){
            //funcString
            int depth = 0;  //current depth
            int targetDepth = -1; //if -1, no target, else target
            String tempString = ""; //tempString to hold complex types

            funcString = cleanParentheses(cleanSpaces(funcString));
            String[] elements = funcString.split(" ");
            if(!elements[1].equals(":")){
                System.out.println("Error, wtf");}


            for(int i =2; i<elements.length;i++){
                String element = elements[i];
                if(targetDepth != -1){
                    switch(element){
                        case "(":
                            depth++;
                            tempString = tempString + " (";
                            break;
                        case ")":
                            depth--;
                            if(depth == targetDepth){
                              //close, back where we want to be
                                types.add(tempString.trim());
                                targetDepth = -1;
                                tempString = "";
                            }else{
                                tempString = tempString + " )";
                            }
                            break;
                        default:
                            //element
                            tempString = tempString + " " + element;
                            break;
                    }
                }
                else{
                    switch(element){
                        case "(":
                            targetDepth = depth;
                            depth++;
                            break;
                        case ")":
                            depth --;
                            break;
                        case "->":
                            System.out.println(element);
                            break;
                        default:
                            types.add(element);
                    }
                }
            }
            arguments = types.size();
            this.name = elements[0];

            //QUESTION: is F : (A-B)-(A-B)-A-(B-(C-(D)) = F A-B-A-B-A-B-C-D
                //Curryingn says A ->(B->(C->D)) = a-b-c-d, but
                //      but (A-B) - (C-D-E) != A-B-C-D.
                //      but (A-B) - (C-(D-E)) = (A-B) - (C-D-E)     THERFOR -> Currying rules, a bracket can be removed if there are no brackets left of it whicha re equal or lower depth!
                //Rule is a bracketted section can be removed if there are no
            //if so then this is easy af and we can make bracketcleaner much simpler
            //Or is it when more than a single function is in brackets
        }

        public List<String> getTypes(){
            List<String> returnList = new ArrayList<>();
            returnList.addAll(types);
            return returnList;
        }

        public String getName(){
            return this.name;
        }
    }


}

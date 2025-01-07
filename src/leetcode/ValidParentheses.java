package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses {
    public boolean isValid(String s) {
        //three types of parentheses <[,(,{>, so 3 counters initialized at 0
        //for each element in the string s, increment or decrement a counter based on the bracket type
        // if any counter is <0 then we know a bracket has been closeed before opening
        // if any counter is not 0 at the end of the string we know they are uneven
        int count_square = 0, count_curl = 0, count_round = 0;

        for(Character c: s.toCharArray()){
            switch(c){
                case '{': count_curl ++;
                    break;
                case '}': count_curl --;
                    break;
                case '(': count_round ++;
                    break;
                case ')': count_round --;
                    break;
                case '[': count_square ++;
                    break;
                case ']': count_square --;
                    break;
            }
            if(count_curl < 0 || count_round <0 || count_square < 0){return false;}
        }
        if(count_curl == 0 &&  count_round ==0 && count_square== 0){return true;}
        else{   return false;}
    }

    public boolean isValid2(String s){
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = Map.of('{','}', '(',')','[',']');
        for(Character c: s.toCharArray()){
            if(c == '{' || c == '(' || c == '['){
                stack.push(map.get(c)); // stack contains the corresponding bracket of the last opened bracket
            }else if(!stack.empty() && c == stack.peek()){
                stack.pop();
            }else {return false;}

        }
        return stack.empty();
    }
}

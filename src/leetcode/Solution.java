package leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    //https://leetcode.com/problems/longest-substring-without-repeating-characters/
    public int lengthOfLongestSubstring(String s) {
        // 0 <= s < 5*10^4
        //simple answer is to cycle through the the string and at each index check forward until same character is found,but that will be very slow
        //abcscewefavsdfweefasdvadf
        //abcdaefg ==> we get abcd quickly, but if we try to skip forward we miss bcdaefg
        // what if we
            // maintain a pointer tail for tail index = 0
            // maintain a pointer head for head index  = 0
            // maintain a 'max digits' reference to track the maximum found
        // iterate through string until tail hits len
        // Index tail -> check if character is in substring,
            // if not, save max = (head-tail) if (head-tail)>max, save substring, continue iterating
            // if so, index head, save substring, continue iterating


        //Warm up for next problem = change use of string here to be a set instead for quickness
        String substring = new String();
        int maxDig = -1;
        int head = 0;

        for(int tail =0; tail <s.length();tail++){
            if(substring.contains(s.substring(tail,tail+1))){
                head = head+1;
                tail = tail-1;
                substring =s.substring(head,tail+1);
            }
            else{
                maxDig = tail-head+1>maxDig? tail-head+1: maxDig;
                substring = s.substring(head,tail+1);
            }
        }
        return maxDig>0?maxDig:0;
    }

    public String longestPalindrome(String s){
        /**
         * 1<= s.length <= 1000
         * I feel like this can be done in a first pass the trick is going to be knowing when the halfway iss
         * How do we know when a palindrome is not a palindrome. Well, it's when the left half isn't the right half.
         * aabbbba
         * cbbd
         * cwefasdfgfdsasdfgfdsa
         */
        /**
         * Next time => implement this but with some of the beter solutions herein:https://leetcode.com/problems/longest-palindromic-substring/solutions/4212564/beats-96-49-5-different-approaches-brute-force-eac-dp-ma-recursion/
         *Also worth optimising my own version as I think it is a faster algorithm
         */
        String ss = new String();
        int p_len_max = 0;
        int i = 0;
        int len = s.length();
        int i_max =0;
        int j_max = 0;
        while(i<len - (p_len_max/2)){   //if p_len_max is odd we are a bit inefficient
            int temp_j = 0;
            for(int j = i+p_len_max; j<len;j++){    //here we start at a length> what we;ve found before, need to manage IOB
                if(s.charAt(i) != s.charAt(j)){
                    break;
                }
                else{

                }

                if(isPalindrome(s.substring(i,j+1))){
                    temp_j = j;
                }
            }
            if (temp_j > 0) {
                p_len_max = temp_j-i +1;    //1-0 = 1,but is actually a2 len string
                i_max = i;
                j_max = temp_j;
            }
            i++;
        }
        ss = s.substring(i_max,j_max+1);
        return ss;
    }

    public boolean isPalindrome(String s){
        int len = s.length();
        if(len%2 != 0){//odd
           s = s.substring(0,len/2) +  s.substring(len/2+1);
           len--;
        }
        for(int i = 0; i<len/2;i++){
            if(s.charAt(i) != s.charAt(len-1-i)){ return false;}
        }
        return true;
    }

    public String longestPalindrome2(String s){
        if(s.length() == 1){return s;}

        int max_len = 0;
        String ss = new String();
        String odd = new String();
        String even = new String();

        for(int i = 0; i<s.length()-1;i++){ //we end before last digit because last digit is always 1 length palindrome, and we alreday have that from first digit
            odd = oddPal(s,i);
            even = evenPal(s,i);
            if(odd.length() >max_len || even.length() >max_len){
                ss = odd.length()>even.length() ? odd:even;
                max_len =  odd.length()>even.length() ? odd.length():even.length();
            }
        }
        return ss;
    }

    public String oddPal(String s, int index){
        int left = index;
        int right = index;
        for(int i =1; index-i >=0 && index+i <s.length();i++){
            if(s.charAt(index-i) != s.charAt(index+i)){
                break;
            }
            left = index - i;
            right = index +i;
        }
        return s.substring(left,right+1);
    }

    public String evenPal(String s, int index){
        int left = index;
        int right = index;
        for(int i =0; index-i >=0 && index+i+1 <s.length();i++){
            if(s.charAt(index-i) != s.charAt(index+i+1)){
                break;
            }
            left = index - i;
            right = index +i+1;
        }
        return s.substring(left,right+1);
    }

    public String convert(String s, int numRows) {
        //This could definitely be done with number theory rather than below burte forcing
        if(s.length()<numRows || numRows ==1){
            return s;
        }
        else{
            Map<Integer,StringBuilder> zigRows = new HashMap<>();
            List<Integer> rowInterator = IntStream.rangeClosed(0,numRows-1)
                    .boxed()
                    .collect(Collectors.toList());
            List<Integer> invertedRowInterator = IntStream.rangeClosed(1,numRows-2)
                    .boxed()
                    .sorted((a,b)->b-a)
                    .collect(Collectors.toList());
            rowInterator.addAll(invertedRowInterator);

            for(int i = 0;i<numRows;i++){
                zigRows.put(i,new StringBuilder());
            }

            int rowIteratorIndex = 0;
            int row;
            for(int i = 0;i<s.length();i++){
                row = rowInterator.get(rowIteratorIndex);
                zigRows.get(row).append(s.charAt(i));
                if(rowIteratorIndex+1 >= rowInterator.size()){
                    rowIteratorIndex = 0;
                }else{
                    rowIteratorIndex ++;
                }
            }
            StringBuilder ss = new StringBuilder();
            for(StringBuilder sb: zigRows.values()){
                ss.append(sb);
            }
            return ss.toString();
        }
    }
}

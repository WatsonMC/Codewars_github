package old;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class AlphabeticAnagrams {

    /**
     * 1. sort word into a list of orderedLetters, from smallest to largest
     * 2. pass word and ordered letters to recursive function  with searchIndex = 0, wordIndex = 0
     *  a. base case = index == word.length-1;
     *      - return wordIndex
     *  b. recursive case:
     *      - @ searchindex, get letter in word = curChar
     *      - get indexChar of curChar in orderedLetters
     *      - get wordIndex subsetlength = #unique permutations/remaining letters
     *      - set wordIndex += subsetLength*indexChar
     *      - remove head of orderedLetters
     *      - call f(remainingLetters, word, searchIndex++, wordIndex)
     */
    public Map<Integer, BigInteger> factorials;

    public BigInteger getIndex(List<String> remainingLetters, String word, int searchIndex, BigInteger wordIndex){
        if(searchIndex == word.length()){return wordIndex;}
        int charIndex = remainingLetters.indexOf(String.valueOf(word.charAt(searchIndex)));
        BigInteger subsetSize = getSubsetSize(remainingLetters);
        wordIndex = subsetSize.multiply(BigInteger.valueOf(charIndex)).add(wordIndex);
        remainingLetters.remove(word.substring(searchIndex,searchIndex+1));
        return getIndex(remainingLetters,word,searchIndex+1,wordIndex);
    }

    public BigInteger getSubsetSize(List<String> remainingLetters){
        int n = remainingLetters.size();
        Map<String,Integer> letterFrequencies = new HashMap<>();
        for(String letter:remainingLetters){
            letterFrequencies.put(letter,letterFrequencies.getOrDefault(letter,0)+1);
        }
        return factorials.get(n).divide(letterFrequencies.keySet().stream()
                .map(x -> factorials.get(letterFrequencies.get(x)))
                .reduce(BigInteger.valueOf(1),(a,b) ->a.multiply(b))).divide(BigInteger.valueOf(n));
    }

    public BigInteger listPosition(String word) {
       initFactorials();
       return getIndex(sortLetters(word), word,0,BigInteger.valueOf(1));
    }

    public void initFactorials(){
        BigInteger fact = BigInteger.valueOf(1);
        factorials  = new HashMap<>();
        factorials.put(0,BigInteger.valueOf(1));
        for(int i =1 ; i<26;i++){
            fact = fact.multiply(BigInteger.valueOf(i));
            factorials.put(i,fact);
        }
    }

    public List<String> sortLetters(String word){
        List<String> letters = new LinkedList<>();
        letters = word.chars()
                .mapToObj(c->(char)c)
                .map(c -> String.valueOf(c))
                .collect(Collectors.toList());
        Collections.sort(letters);
        return letters;
    }
}

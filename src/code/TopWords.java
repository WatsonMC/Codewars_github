package code;

import java.util.*;

public class TopWords {

    public static List<String> top3(String s) {
        //create tempString
        //create dict to save count, word
        // iterate through the array of words from s
        // for each word in string s
            // use index of to remove word

        //Words also contain apostrophes, so replace anything that's not a word, number or apostrophe with space
        String tempString = s;
        String replacedString = s.replaceAll("[^a-zA-Z0-9']", " ").replaceAll("[\\s]{0,}","\\s");
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(
                s.replaceAll("[^a-zA-Z0-9']"," ")
                        .replaceAll("[ ]+"," ")
                        .trim()
                        .split(" ")));

        Map<Integer, List<String>> wordCountMap = new HashMap<>();
        for(String word: uniqueWords){
            Integer wordCount = 0;
            while(tempString.indexOf(word)!=-1){
                wordCount++;
                tempString =  tempString.replaceFirst(word,"");
            }
            if(!wordCountMap.containsKey(new Integer(wordCount))){
                wordCountMap.put(new Integer(wordCount),new ArrayList<>());
                wordCountMap.get(wordCount).add(word);
            }
            else{
                wordCountMap.get(wordCount).add(word);
            }
        }
        return null;
    }
}
package code;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopWords {

    public static List<String> top3(String s) {
        //create tempString
        //create dict to save count, word
        // iterate through the array of words from s
        // for each word in string s
            // use index of to remove word

        //Words also contain apostrophes, so replace anything that's not a word, number or apostrophe with space
        String replacedString = s.replaceAll("[^a-zA-Z0-9']", " ").replaceAll("[\\s]{0,}","\\s");
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(
                s.replaceAll("[^a-zA-Z0-9']"," ")
                        .replaceAll("[ ]+"," ")
                        .trim()
                        .split(" ")));
        return null;
    }
}
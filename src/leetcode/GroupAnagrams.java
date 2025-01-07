package leetcode;

import java.sql.Array;
import java.util.*;

public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Map<Character, Integer>, List<String>> stringProfiles = new HashMap<>();
        for (String s : strs) {
            Map<Character, Integer> s_map = new HashMap<>();
            for (Character c : s.toCharArray()) {
                s_map.put(c, s_map.getOrDefault(c, 0) + 1);
            }
            if (stringProfiles.containsKey(s_map)) {
                //anagram found
                stringProfiles.get(s_map).add(s);   //add s to list of anagrams
            } else {
                //new anagram profile
                stringProfiles.put(s_map, new ArrayList<>(Arrays.asList(s)));
            }
        }
        return new ArrayList<List<String>>(stringProfiles.values());

    }
        public List<List<String>> groupAnagramsSimpler(String[] strs){
            //to do this quicker we do the same thing, but need to simplify how we store string profiles.
            // rather than creating a map with maps as the key, we can simply sort the string and use the sorted string as a key

            Map<String, List<String>> map = new HashMap<>();

            for(String word: strs) {
                char[] wordChars = word.toCharArray();
                Arrays.sort(wordChars);
                String wordSorted = new String(wordChars);
                if(map.containsKey(wordSorted)){
                    map.get(wordSorted).add(word);
                }
                else{
                    map.put(wordSorted, new ArrayList<>(Arrays.asList(word)));
                }
            }
            return new ArrayList<>(map.values());
        }

}





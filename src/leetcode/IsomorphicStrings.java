package leetcode;

import java.util.HashMap;
import java.util.Map;

public class IsomorphicStrings {

    public boolean isIsomorphic(String s, String t) {
        //Isomorphic if the letters in s can be mapped to make t
        // i.e. add and egg => a -> e, d-> g, turns add to egg
        //add and sub -> a-> s, d-> u, but no way to create the b
        //  only if the two words have the same number of unique digits
        //  only if

        //algorithm:
        // iterate through each character of the string s, creating a map of K:V, K= uniqueu characters in s, V = mapping value to produce t
        // for each charcter c
        //       if no mapping, map.c = t[i]
        //       if map.c and map.c<> t[i] then failure
        //

        Map<Character, Character> map_s_t = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c_s = s.charAt(i);
            Character c_t = t.charAt(i);
            if (!map_s_t.containsKey(c_s)) {
                if (map_s_t.containsValue(c_t)) {
                    return false;
                }
                map_s_t.put(c_s, c_t);
            } else if (!map_s_t.get(c_s).equals(c_t)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIsomorphic2(String s, String t) {
        //this time he's using two int arrays to house the indexes of each letter in the given strings
        Map<Character, Integer> indexS = new HashMap<>();
        Map<Character, Integer> indexT = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (!indexS.getOrDefault(s.charAt(i), 0).equals(indexT.getOrDefault(t.charAt(i), 0))) {
                return false;
            }

            indexS.put(s.charAt(i), i + 1);
            indexT.put(t.charAt(i), i + 1);
        }
        return true;
    }
}


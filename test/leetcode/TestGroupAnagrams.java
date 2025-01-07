package leetcode;

import org.junit.Test;

import java.util.List;

public class TestGroupAnagrams {

    @Test
    public void test(){
        GroupAnagrams ga = new GroupAnagrams();

        String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};

        List<List<String>> results = ga.groupAnagramsSimpler(strs);
    }
}

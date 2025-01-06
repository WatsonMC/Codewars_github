import leetcode.Solution;
import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {

    Solution sol = new Solution();

    @Test
    public void testLongestSubstring(){
        String s1 = "pwwkew";
        String s2 = "aaa";
        String s3 = "abcadefg";

        Assert.assertTrue(sol.lengthOfLongestSubstring(s1) == 3);
        Assert.assertTrue(sol.lengthOfLongestSubstring(s2) == 1);
        Assert.assertTrue(sol.lengthOfLongestSubstring(s3) == 7);
    }

    @Test
    public void isPalindromeTest(){
        Assert.assertTrue(sol.isPalindrome("s"));
        Assert.assertTrue(sol.isPalindrome("sas"));
        Assert.assertFalse(sol.isPalindrome("babad"));
    }

    @Test
    public void longestPalindromeTest(){
        Assert.assertTrue(sol.longestPalindrome("babad").equals("bab"));
    }

    @Test
    public void longestPalindromeTest2(){
        Assert.assertEquals(sol.evenPal("cbbd",1),"bb");
        Assert.assertEquals(sol.evenPal("bs",1),"s");
        Assert.assertEquals(sol.oddPal("abcdefgth",3),"d");
        Assert.assertEquals(sol.longestPalindrome2("cbbd"),"bb");
    }

    @Test
    public void testConvert(){
        sol.convert("ABCDEFGHIJK",4);
        sol.convert("ABCDEFGHIJK",3);
    }
}

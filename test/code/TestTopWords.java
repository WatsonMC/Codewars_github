package code;

import org.junit.Test;

public class TestTopWords {

    @Test
    public void testGeneral(){
        String testString = "AAA \\ADSF //asdf Af'sdf a''fse\n asdf\nasdf";
        TopWords.top3(testString);
    }
}

package code;

import org.junit.Test;

public class TestTopWords {

    @Test
    public void testGeneral(){
        String testString = "nasdf AAA AAA AAA \\ADSF nasdf //asdf Af'sdf a''fse\n asdf\nasdf asdf nasdf asdf nasdf  " ;
        TopWords.top3(testString);
    }
}

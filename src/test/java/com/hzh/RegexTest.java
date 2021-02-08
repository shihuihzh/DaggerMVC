package com.hzh;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void testGetTokenFromPath() {
        final Pattern pattern = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9]+)}");

        String testPath = "/hello/{aa}/{bb}/{aa22cc}/{9909}";
        final Matcher matcher = pattern.matcher(testPath);
        while(matcher.find()) {
            System.out.println(matcher.group(1));
        }

        final String s = matcher.replaceAll(".*");
        System.out.println(s);

    }
}

package com.sdlab.sdlab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupValidator {

    private static final String GROUP_PATTERN = "^[0-9]{5}$";

    private static Pattern pattern = Pattern.compile(GROUP_PATTERN);

    public static boolean validate(String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();

    }
}

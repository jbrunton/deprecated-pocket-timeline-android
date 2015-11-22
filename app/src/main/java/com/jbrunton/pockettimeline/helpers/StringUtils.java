package com.jbrunton.pockettimeline.helpers;

public class StringUtils {
    public static boolean nullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        if ("".equals(s)) {
            return true;
        }

        return false;
    }

    public static boolean nullOrEmpty(CharSequence s) {
        return nullOrEmpty(s.toString());
    }
}

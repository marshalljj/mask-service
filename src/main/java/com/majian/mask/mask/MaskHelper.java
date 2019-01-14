package com.majian.mask.mask;

public class MaskHelper {

    public static String mask(String content, int begin, int end) {
        if (content == null) {
            return null;
        }
        if (end==-1) {
            end = content.length();
        }

        int leftBegin = begin - 1;
        int rightEnd = end - 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i >= leftBegin && i <= rightEnd) {
                result.append("*");
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }
}

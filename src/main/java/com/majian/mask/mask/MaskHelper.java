package com.majian.mask.mask;

public class MaskHelper {

    private MaskHelper() {
    }

    public static String maskMiddle(String content, int begin, int end) {
        if (!validateIdx(content, begin, end)) {
            return content;
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

    private static boolean validateIdx(String content, int begin, int end) {
        int size = content.length();
        if (begin <= 0 || begin > size) {
            return false;
        }
        if (end <= 0 || end > size) {
            return false;
        }
        if (begin > end) {
            return false;
        }
        return true;
    }

}

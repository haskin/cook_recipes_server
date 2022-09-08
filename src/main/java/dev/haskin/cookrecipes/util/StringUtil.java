package dev.haskin.cookrecipes.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {

    private StringUtil() {
    }

    public static String toProperCase(String words) {
        if (words.isBlank())
            return "";
        String[] wordsToFormat = words.trim().split("\\s+");
        return Arrays.stream(wordsToFormat)
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}

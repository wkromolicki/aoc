package common;

import java.util.List;
import java.util.stream.Collectors;

public class Strings2 {
    public static Pair<String, String> splitInHalf(String s) {
        return Pair.of(s.substring(0, s.length() / 2), s.substring(s.length() / 2));
    }

    public static Pair<String, String> splitOn(String s, String c) {
        return Pair.of(s.substring(0, s.indexOf(c)), s.substring(s.indexOf(c) + 1));
    }

    public static List<String> characters(String s) {
        return s.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
    }
}

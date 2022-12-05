package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Common {
    public static List<String> lines(String fileName) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(Common.class.getClassLoader().getResourceAsStream(fileName)))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void println(Object o) {
        System.out.println(toString(o));
    }
    public static void println(String prefix, Object o) {
        System.out.println(prefix + toString(o));
    }


    public static String toString(Object o){
        return switch (o) {
            case null -> "null";
            default -> o.toString();
        };
    }

    public static class Strings {
        public static Pair<String, String> splitInHalf(String s){
            return Pair.of(s.substring(0, s.length() / 2), s.substring(s.length() / 2 ));
        }

        public static List<String> characters(String s) {
            return s.chars().mapToObj(c -> String.valueOf((char)c)).collect(Collectors.toList());
        }
    }



}

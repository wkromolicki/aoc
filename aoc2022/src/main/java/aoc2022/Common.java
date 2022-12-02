package aoc2022;

import common.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    public static class Aggregation {

        public static <T> List<T> merge(List<T> c1, List<T> c2) {
            var r = new ArrayList<T>();
            r.addAll(c1);
            r.addAll(c2);
            return r;
        }

        public static <T> List<T> add(List<T> list, T item){
            return merge(list, List.of(item));
        }

        public static <T> List<T> copy(List<T> c1) {
            return merge(c1, List.of());
        }
        public static <I> List<List<I>> groupWhen(
                List<I> input,
                Predicate<I> newGroupCondition
        ){
            var result = input.stream().collect(() -> new Pair<>(new ArrayList<List<I>>(), new ArrayList<I>()), (acc, val) -> {
                switch(val) {
                    case I i when newGroupCondition.test(i) -> {
                        acc.a().add(copy(acc.b()));
                        acc.b().clear();
                    }
                    case I l -> {
                        acc.b().add(l);
                    }
                }
            }, (p1, p2) -> new Pair<>(merge(p1.a(), p2.a()), merge(p1.b(), p2.b())));
            return add(result.a(), result.b());
        }

    }

}

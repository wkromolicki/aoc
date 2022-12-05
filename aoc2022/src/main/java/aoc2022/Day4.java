package aoc2022;

import com.google.common.collect.Range;
import common.Console2;
import common.Pair;

import static common.Strings2.splitOn;
import static common.Console2.println;

public class Day4 {

    public static void main(String[] args) {
        var lines = Console2.lines("aoc2022/day4.txt");

        var enclosing = lines.stream().map(l -> enclose(ranges(l))).filter(p -> p).count();

        println("Enclosing: " + enclosing);

        var overlapping = lines.stream().map(l -> overlap(ranges(l))).filter(p -> p).count();

        println("overlapping: " + overlapping);
    }

    public static Range<Integer> range(String s) {
        var p = splitOn(s, "-");

        return Range.closed(Integer.parseInt(p.a()), Integer.parseInt(p.b()));
    }

    public static Pair<Range<Integer>, Range<Integer>> ranges(String s) {
        var p = splitOn(s, ",");
        return Pair.of(range(p.a()), range(p.b()));
    }

    public static boolean enclose(Pair<Range<Integer>, Range<Integer>> p) {
        return p.a().encloses(p.b()) || p.b().encloses(p.a());
    }

    public static boolean overlap(Pair<Range<Integer>, Range<Integer>> p) {

        return p.a().isConnected(p.b());
    }
}

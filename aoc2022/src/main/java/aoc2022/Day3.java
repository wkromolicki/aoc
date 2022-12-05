package aoc2022;

import common.CollectionOps;
import common.Common;
import common.Common.Strings2;
import common.Pair;

import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static common.CollectionOps.groupBy;
import static common.CollectionOps.toSet;
import static common.Common.Strings2.splitInHalf;
import static common.Common.println;

public class Day3 {

    public static void main(String[] args) {

        var sum = Common.lines("aoc2022/day3.txt").stream().mapToInt(l -> sumPriorities(commonTypes(splitInHalf(l)), Priorities::priority)).sum();

        println("Part1: ", sum);

        var groupedBy3 = groupBy(Common.lines("aoc2022/day3.txt").stream().map(Strings2::characters).collect(Collectors.toList()), 3);

        var sumBadges = groupedBy3.stream().mapToInt(Day3::badges).sum();
        println("Part2: " + sumBadges);

    }

    static Set<String> commonTypes(Pair<String, String> rucksack) {
        var left = Strings2.characters(rucksack.a());
        var right = Strings2.characters(rucksack.b());
        return CollectionOps.intersect(toSet(left), toSet(right));
    }

    static int badges(List<List<String>> threeSets) {
        var badges = CollectionOps.intersect(threeSets.stream().map(CollectionOps::toSet).collect(Collectors.toList()));

        return sumPriorities(badges, Priorities::priority);
    }

    static int sumPriorities(Set<String> input, ToIntFunction<String> priority) {
        return input.stream().mapToInt(priority).sum();
    }
public static class Priorities {
    static String p = "abcdefghijklmnopqrstuvwxyz";
    static String p2 = p + p.toUpperCase();

    static int priority(String s) {
        return p2.indexOf(s) + 1;
    }
}
}



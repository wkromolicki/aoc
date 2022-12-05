package aoc2022;

import common.CollectionOps;
import common.Console2;

import java.util.*;
import java.util.stream.Collectors;

import static common.Console2.println;

public class Day1 {
    public static void main(String[] args) {
        var fileName = "aoc2022/day1-1.txt";
        var lines = Console2.lines(fileName);

        var calories = readCalories2(lines);
        var firstElfWasCarrying = calories.stream().findFirst().orElse(0);
        println("First elf got:", firstElfWasCarrying); //66487

        var top3 = calories.stream().limit(3).mapToInt(Integer::intValue).sum(); //197301

        println("Top3 elves got:", top3);
    }

    static Collection<Integer> readCalories(List<String> records) {
        Set<Integer> calories = new TreeSet<>(Comparator.reverseOrder());
        Stack<Integer> currentCount = new Stack<>();

        currentCount.push(0);
        records.forEach(r -> {
            switch(r) {
                case String s when s.isBlank() -> {
                    Integer curr = currentCount.pop();
                    calories.add(curr);
                    currentCount.push(0);
                }
                case String l -> {
                    Integer val = Integer.parseInt(l);
                    Integer c = currentCount.pop();
                    currentCount.push(val + c);
                }
            }
        });

    return calories;
    }


    static List<Integer> readCalories2(List<String> records) {
        var grouped = CollectionOps.groupWhen(records, String::isBlank);

        return grouped.stream().map(l -> l.stream().mapToInt(Integer::parseInt).sum()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }


}


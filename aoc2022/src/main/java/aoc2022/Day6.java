package aoc2022;

import common.Console2;

import static common.Console2.println;

public class Day6 {
    public static void main(String[] args) {
        var line = Console2.lines("aoc2022/day6.txt").get(0);
        println("part1:", solve(line, 4));
        println("part2:", solve(line, 14));
    }

    static int solve(String line, int n) {
        for(int p=n; p < line.length(); p++)
            if (line.substring(p - n, p).chars().distinct().count() == n) return p;
        return -1;
    }
}

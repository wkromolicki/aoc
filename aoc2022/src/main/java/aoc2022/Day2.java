package aoc2022;

import common.MultiMap;
import common.Pair;

import java.util.Map;

import static aoc2022.Common.println;

public class Day2 {
    public static void main(String[] args) {
        var fileName = "aoc2022/day2-2.txt";
        var lines = Common.lines(fileName);

        var score1 = lines.stream().mapToInt(Day2::part1).sum();

        println(score1);

        var score2 = lines.stream().mapToInt(Day2::part2).sum();

        println(score2);


    }
    //A,X - Rock1, B,Y - Paper2, C,Z -> Scissors3
    static int part1(String line) {
        return switch(line) {
            case "A X" -> 3 + 1;
            case "A Y" -> 6 + 2;
            case "A Z" -> 0 + 3;

            case "B X" -> 0 + 1;
            case "B Y" -> 3 + 2;
            case "B Z" -> 6 + 3;

            case "C X" -> 6 + 1;
            case "C Y" -> 0 + 2;
            case "C Z" -> 3 + 3;

            default -> 0;
        };
    }
    //A - Rock1, B - Paper2, C -> Scissors3, X lose, Y draw, Z win
    static int part2(String line) {
        return switch(line) {
            case "A X" -> 0 + 3;
            case "A Y" -> 3 + 1;
            case "A Z" -> 6 + 2;

            case "B X" -> 0 + 1;
            case "B Y" -> 3 + 2;
            case "B Z" -> 6 + 3;

            case "C X" -> 0 + 2;
            case "C Y" -> 3 + 3;
            case "C Z" -> 6 + 1;

            default -> 0;
        };
    }
}

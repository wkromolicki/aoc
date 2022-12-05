package aoc2022;

import common.Common;

import static common.Common.println;
import static aoc2022.Day2.Constants.*;

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
            case "A X" -> DRAW + ROCK;
            case "A Y" -> WIN + PAPER;
            case "A Z" -> LOSS + SCISSORS;

            case "B X" -> LOSS + ROCK;
            case "B Y" -> DRAW + PAPER;
            case "B Z" -> WIN + SCISSORS;

            case "C X" -> WIN + ROCK;
            case "C Y" -> LOSS + PAPER;
            case "C Z" -> DRAW + SCISSORS;

            default -> NOTHING;
        };
    }
    //A - Rock1, B - Paper2, C -> Scissors3, X lose, Y draw, Z win
    static int part2(String line) {
        return switch(line) {
            case "A X" -> LOSS + SCISSORS;
            case "A Y" -> DRAW + ROCK;
            case "A Z" -> WIN + PAPER;

            case "B X" -> LOSS + ROCK;
            case "B Y" -> DRAW + PAPER;
            case "B Z" -> WIN + SCISSORS;

            case "C X" -> LOSS + PAPER;
            case "C Y" -> DRAW + SCISSORS;
            case "C Z" -> WIN + ROCK;

            default -> 0;
        };
    }

    public static class Constants {
        static int WIN = 6;
        static int DRAW = 3;
        static int LOSS = 0;
        static int ROCK = 1;
        static int PAPER = 2;
        static int SCISSORS = 3;
        static int NOTHING = 0;
    }
}

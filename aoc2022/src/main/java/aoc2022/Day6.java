package aoc2022;

import common.Console2;
import common.Strings2;

import static common.Console2.println;

public class Day6 {
    public static void main(String[] args) {
        var line = Console2.lines("aoc2022/day6.txt").get(0);

        println("part1: ",findNDifferentSymbols(line, 4));

        println("part2: ", findNDifferentSymbols(line, 14));
    }

    static int findNDifferentSymbols(String line, int n) {
        var distinctBuffer = new DistinctBuffer(n);
        var allChars = Strings2.characters(line).stream().takeWhile(distinctBuffer::isNotFull).count();
        return (int)allChars + 1;
    }

    static class DistinctBuffer {
        StringBuilder lastChars = new StringBuilder();
        final int size;

        DistinctBuffer(int size) {
            this.size = size;
        }

        boolean isNotFull(String s) {
            int firstIndex = lastChars.indexOf(s);
            lastChars.append(s);
            if (firstIndex == -1) {
                return lastChars.length() != size;
            } else {
                lastChars.delete(0, firstIndex + 1);
            }
            return true;
        }
    }
}
package aoc2022;

import common.Console2;
import common.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static common.Console2.println;

public class Day7 {

    public static void main(String[] args) {
        var lines = Console2.lines("aoc2022/day7.txt");

        var result = solve(lines);
        println("part1: ", result.a());
        println("part2: ", result.b());
    }

    static Pair<Integer, Integer> solve(List<String> lines) {
        String currentDir = "";
        Map<String, Integer> allDirs = new HashMap<>();

        List<String> files = new ArrayList<>();
        for(String l: lines) {
            if(l.startsWith("$ cd")) {
                String dir = l.substring("$ cd".length()).trim();
                if(dir.startsWith("..")) {
                    currentDir = currentDir.substring(0, currentDir.lastIndexOf("/") - 1);
                } else if (dir.equals("/")){
                    currentDir = "/";
                } else {
                    currentDir = currentDir  + dir + "/";
                }
            } else if (l.startsWith("$ ls")){
                allDirs.put(currentDir, 0);
            } else {
                if(!l.startsWith("dir")) {
                    String size = l.substring(0, l.indexOf(" "));
                    String file = l.substring(l.indexOf(" ") + 1);
                    String fileLine = currentDir + size; //add only size information
                    files.add(fileLine);
                }
            }
        }
        //now count sizes
        Map<String, Integer> mapWithSize = calculateDirSizes(allDirs, files);

        var maxSize = 100000;
        var maxSpace = 70000000;
        var required = 30000000;

        var sizeOfDirs = mapWithSize.values().stream().filter(integer -> integer <= maxSize).mapToInt(i -> i).sum();

        var occupiedSpace = mapWithSize.get("/");

        var unusedSpace = maxSpace - occupiedSpace;

        var sizeOfDirToDelete = mapWithSize.values().stream().filter(integer -> integer >= required - unusedSpace).sorted().findFirst().orElse(0);

        return Pair.of(sizeOfDirs, sizeOfDirToDelete);
    }

    static Map<String, Integer> calculateDirSizes(Map<String, Integer> allDirs, List<String> files) {
        return allDirs.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> files.stream().filter(s -> s.startsWith(e.getKey())).mapToInt(
                        f -> Integer.parseInt(f.substring(f.lastIndexOf("/") + 1))
                ).sum() + e.getValue()
        ));
    }
}

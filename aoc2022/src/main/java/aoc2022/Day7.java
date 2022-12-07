package aoc2022;

import common.Console2;

import java.util.*;
import java.util.stream.Collectors;

import static common.Console2.println;

public class Day7 {

    public static void main(String[] args) {
        var lines = Console2.lines("aoc2022/day7.txt");

        var directoriesWithSizes = readDirInfo(lines);

        println("part1: ", part1(directoriesWithSizes));
        println("part2: ", part2(directoriesWithSizes));

        //part1: 1084134
        //part2: 6183184
    }

    static int part1(Map<String, Integer> directories) {
        var maxSize = 100000;
        return directories.values().stream().filter(integer -> integer <= maxSize).mapToInt(i -> i).sum();
    }

    static int part2(Map<String, Integer> directories) {
        var maxSpace = 70000000;
        var required = 30000000;
        var occupiedSpace = directories.get("/");
        var unusedSpace = maxSpace - occupiedSpace;
        return directories.values().stream().filter(integer -> integer >= required - unusedSpace).sorted().findFirst().orElse(0);
    }
    
    static Map<String, Integer> readDirInfo(List<String> lines) {
        String currentDir = "";
        Map<String, Integer> allDirs = new HashMap<>();
        List<String> files = new ArrayList<>();
        
        for(String l: lines) {
            switch (l) {
                case String s when s.startsWith("$ cd") -> currentDir = cd(currentDir, s.substring("$ cd".length()).trim());
                case String s when s.startsWith("$ ls") -> ls(currentDir, allDirs);
                default -> file(currentDir, files, l);
            }
        }
        return calculateDirSizes(allDirs, files);
    }

    private static void file(String currentDir, List<String> files, String l) {
        if(!l.startsWith("dir")) {
            String size = l.substring(0, l.indexOf(" "));
            String fileLine = currentDir + size; //add only size information
            files.add(fileLine);
        }
    }

    private static void ls(String currentDir, Map<String, Integer> allDirs) {
        allDirs.put(currentDir, 0);
    }

    static String cd(String current, String dir) {
        if(dir.startsWith("..")) {
            return current.substring(0, current.lastIndexOf("/") - 1);
        } else if (dir.equals("/")){
            return "/";
        } else {
            return current  + dir + "/";
        }
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

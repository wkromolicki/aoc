package aoc2022;

import common.Console2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

import java.util.stream.IntStream;

import static common.Console2.println;

public class Day8 {

    public static void main(String[] args) {
        var lines = Console2.lines("aoc2022/day8.txt");

        var forest = Forest.read(lines);

        println("part1: ", forest.countVisible());
        println("part2: ", forest.highestViewingDistance());

        //part1: 1776
        //part2: 234416
    }


    public static class Forest {

        private final List<List<Integer>> forest;
        private final int width;
        private final int height;

        public Forest(List<List<Integer>> forest) {
            this.forest = forest;
            this.width = forest.size();
            this.height = forest.get(0).size();
        }


        int get(int x, int y ){
            return forest.get(y).get(x);
        }
        long countVisible() {
            return forAllTrees((y,x) -> (isVisible(x,y))? 1L : 0L, Long::sum);
        }

        long highestViewingDistance() {
            return forAllTrees(this::scenicScore, (acc, curr) -> (curr >= acc)?curr:acc );
        }

        long forAllTrees(BiFunction<Integer, Integer, Long> treeF, BiFunction<Long, Long, Long> acc) {
            long initial = 0;
            for(int c = 0; c < forest.size(); c++){
                for(int r = 0; r <forest.get(c).size(); r++) {
                    initial = acc.apply(initial, treeF.apply(c, r));
                }
            }
            return initial;
        }

        boolean isVisible(int x, int y) {
            int treeHeight = get(x,y);
            var left = treesFromTo(x, 0).map(x1 -> get(x1, y)).allMatch(h -> h < treeHeight);
            var top = treesFromTo(y, 0).map(y1 -> get(x, y1)).allMatch(h -> h < treeHeight);
            var right = treesFromTo(x, width -1).map(x1 -> get(x1, y)).allMatch(h -> h < treeHeight);
            var bottom = treesFromTo(y, height -1).map(y1 -> get(x, y1)).allMatch(h -> h < treeHeight);
            return left || right || top || bottom;
        }

        long scenicScore(int x, int y) {
            int treeHeight = get(x,y);
            var left = treesFromTo(x , 0).map(x1 -> get(x1, y)).takeWhile(new ViewUntil(treeHeight)).count();
            var top = treesFromTo(y , 0).map(y1 -> get(x, y1)).takeWhile(new ViewUntil(treeHeight)).count();
            var right = treesFromTo(x , width - 1).map(x1 -> get(x1, y)).takeWhile(new ViewUntil(treeHeight)).count();
            var bottom = treesFromTo(y, height -1).map(y1 -> get(x, y1)).takeWhile(new ViewUntil(treeHeight)).count();
            return left * top * right * bottom;
        }

        static class ViewUntil implements IntPredicate {
            private boolean stop = false;
            private final int max;

            ViewUntil(int max) {
                this.max = max;
            }

            @Override
            public boolean test(int i) {
                if (stop) {
                    return false;
                } else {
                    if (i >= max) {
                        stop = true;
                    }
                    return true;
                }
            }
        }

        static Forest read(List<String> lines) {
            List<List<Integer>> forest = new ArrayList<>();
            for(int y = 0; y < lines.size(); y ++) {
                String[] l = lines.get(y).split("");
                for (String s : l) {
                    if (forest.size() <= y) {
                        forest.add(new ArrayList<>());

                    }
                    forest.get(y).add(Integer.parseInt(s));
                }
            }
            return new Forest(forest);
        }

        public static IntStream treesFromTo(int start, int end) {
            if (start == end) {
                return IntStream.empty();
            } else {
                var step = (start <= end) ? 1 : -1;
                return IntStream.iterate(start + step, p -> p + step).limit(Math.abs(end - start));
            }
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (List<Integer> integers : forest) {
                for (int x = 0; x < integers.size(); x++) {
                    sb.append(integers.get(x)).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

}

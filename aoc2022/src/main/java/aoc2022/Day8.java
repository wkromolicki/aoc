package aoc2022;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import common.Console2;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static common.Console2.println;

public class Day8 {



    public static void main(String[] args) {
        var lines = Console2.lines("aoc2022/day8.txt");

        var grid = Forest.read(lines);

        println(grid);

        println("part1: ", grid.countVisible()); //1776
        println("part2: ", grid.highestViewingDistance());
    }


    public static class Forest {

        private final Table<Integer, Integer, Integer> t;
        private final int width;
        private final int height;

        public Forest(Table<Integer, Integer, Integer> t) {
            this.t = t;
            this.width = t.columnKeySet().size();
            this.height = t.rowKeySet().size();
        }


        int get(int x, int y ){
            return t.get(x,y);
        }
        long countVisible() {
            return t.cellSet().stream().filter(c -> isVisible(c.getColumnKey(), c.getRowKey())).count();
        }

        long highestViewingDistance() {
            return t.cellSet().stream().mapToLong(c -> scenicScore(c.getColumnKey(), c.getRowKey())).max().orElse(0);
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
            var left = treesFromTo(x , 0).map(x1 -> t.get(x1, y)).takeWhile(new ViewUntil(treeHeight)).count();
            var top = treesFromTo(y , 0).map(y1 -> t.get(x, y1)).takeWhile(new ViewUntil(treeHeight)).count();
            var right = treesFromTo(x , width - 1).map(x1 -> t.get(x1, y)).takeWhile(new ViewUntil(treeHeight)).count();
            var bottom = treesFromTo(y, height -1).map(y1 -> t.get(x, y1)).takeWhile(new ViewUntil(treeHeight)).count();

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
            Table<Integer, Integer, Integer> t = HashBasedTable.create();

            for(int i=0; i < lines.size(); i ++) {
                String[] l = lines.get(i).split("");
                for(int j=0;j<l.length;j++) {
                    t.put(j,i,Integer.parseInt(l[j]));
                }
            }
            return new Forest(t);
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
            for(Integer ci :t.columnKeySet()) {
                for(Integer c:t.column(ci).values()) {
                    sb.append(c).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

}

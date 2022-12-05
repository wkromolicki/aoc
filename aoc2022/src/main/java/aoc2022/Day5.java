package aoc2022;

import common.Console2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static common.Strings2.characters;
import static common.Console2.println;

public class Day5 {
    public static void main(String[] args) {

        var stacks = readInitial();

        var moves = readMoves(
                Console2.lines("aoc2022/day5.txt").stream()
                        .dropWhile(s -> !s.isBlank())
                        .dropWhile(String::isBlank)
                        .collect(Collectors.toList())
        );

        moves.forEach(stacks::move9000);

        println("Part 1: ",stacks.tops());

        stacks = readInitial();

        moves.forEach(stacks::move9001);

        println("Part 2: ", stacks.tops());
    }


    public static Stacks readInitial() {
        return new Stacks();
    }

    public static List<Move> readMoves(List<String> in) {

        return in.stream().map(Move::parseMove).collect(Collectors.toList());
    }

    public record Move(int howMany, int from, int to){
        static Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        //move 3 from 8 to 2
        static Move parseMove(String in) {
            Matcher m = pattern.matcher(in);
            m.matches();
            return new Move(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
        }
    }

    public static class Stacks {

        private final static Map<Integer, Stack<String>> stacks = new HashMap<>();

        public Stacks() {
            for(int i = 1; i <=9 ; i++ ){
                stacks.put(i, new Stack<>());
            }
            push("TFVZCWSQ", 1);
            push("BRQ", 2);
            push("SMPQTZB", 3);
            push("HQRFVD", 4);
            push("PTSBDLGJ", 5);
            push("ZTRW", 6);
            push("JRFSNMQH", 7);
            push("WHFNR", 8);
            push("BRPQTZJ", 9);
        }

        public void move9000(Move m) {
            var from = stacks.get(m.from);
            var to = stacks.get(m.to);
            for (int i = 0; i < m.howMany; i ++){
                to.push(from.pop());
            }
        }

        public void move9001(Move m) {
            var from = stacks.get(m.from);
            var to = stacks.get(m.to);

            var tmp = new Stack<String>();
            for (int i = 0; i < m.howMany; i ++){
                tmp.push(from.pop());
            }
            for (int i = 0; i < m.howMany; i ++){
                to.push(tmp.pop());
            }
        }

        public String tops() {
            return stacks.values().stream().map(Stack::peek).collect(Collectors.joining());
        }

        private static void push(String s, int stackNumber) {
            Stack<String> stack = stacks.get(stackNumber);
            characters(new StringBuilder(s).reverse().toString()).forEach(stack::push);
        }
    }
}

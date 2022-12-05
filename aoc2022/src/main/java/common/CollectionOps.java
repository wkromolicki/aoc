package common;

import java.util.*;
import java.util.function.Predicate;

public class CollectionOps {

    public static <T> Set<T> intersect(Set<T> left, Set<T> right) {
        return intersect(List.of(left,right));
    }

    public static <T> Set<T> toSet(Collection<T> c) {
        return new HashSet<>(c);
    }

    public static <T> Set<T> intersect(List<Set<T>> sets) {


        if (sets.isEmpty()) {
            return Set.of();
        } else {
            var intersection = new HashSet<>(sets.stream().findFirst().get());

            sets.forEach(intersection::retainAll);
            return intersection;
        }

    }

    public static <T> List<T> merge(List<T> c1, List<T> c2) {
        var r = new ArrayList<T>();
        r.addAll(c1);
        r.addAll(c2);
        return r;
    }

    public static <T> List<T> add(List<T> list, T item){
        return merge(list, List.of(item));
    }

    public static <T> List<T> copy(List<T> c1) {
        return merge(c1, List.of());
    }
    public static <I> List<List<I>> groupWhen(
            List<I> input,
            Predicate<I> newGroupCondition
    ){
        var result = input.stream().collect(() -> new Pair<>(new ArrayList<List<I>>(), new ArrayList<I>()), (acc, val) -> {
            switch(val) {
                case I i when newGroupCondition.test(i) -> {
                    acc.b().add(i);
                    acc.a().add(copy(acc.b()));
                    acc.b().clear();
                }
                case I l -> {
                    acc.b().add(l);
                }
            }
        }, (p1, p2) -> new Pair<>(merge(p1.a(), p2.a()), merge(p1.b(), p2.b())));
        return add(result.a(), result.b());
    }

    public static <I> List<List<I>> groupBy(List<I> input, int size) {
        var counter = new ResettingCounter(size);
        return groupWhen(input, c -> counter.wasReset());
    }


    public static class Counter {
        private int count;

        public Counter(int count) {
            this.count = count;
        }

        public boolean countDown() {
            count = count - 1;
            return count <= 0;
        }


    }

    public static class ResettingCounter{
        private final int count;

        private int v = 0;
        public ResettingCounter(int count) {
            this.count = count;
        }

        public boolean wasReset() {
            v = v +1;
            if(v < count) {
                return false;
            } else {
                v = 0;
                return true;
            }

        }
    }
}

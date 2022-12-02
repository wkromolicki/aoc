package common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MultiMap<K1, K2, V> {
    private final Map<K1, Map<K2, V>> internalMap = new HashMap<>();

    public MultiMap<K1, K2, V> put(K1 k1, K2 k2, V v) {
        internalMap.computeIfAbsent(k1, k -> new HashMap<>()).put(k2, v);
        return this;
    }

    public static <K1, K2, V> MultiMap<K1, K2, V> empty() {
        return new MultiMap<>();
    }

    public Optional<V> get(K1 k1, K2 k2) {
        return Optional.ofNullable(internalMap.getOrDefault(k1, Map.of()).get(k2));
    }

    public void clear() {
        internalMap.forEach((k,v) -> v.clear());
        internalMap.clear();
    }
}

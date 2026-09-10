package com.example.lru;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        System.out.println(cache);
        cache.get("a"); // access a -> becomes most-recent
        cache.put("d", "4"); // evict least-recent (b)
        System.out.println(cache);

        Optional<String> maybe = cache.get("b");
        System.out.println("get(b) present? " + maybe.isPresent());

        // Thread-safe wrapper example
        ThreadSafeLRUCache<String, String> safe = new ThreadSafeLRUCache<>(2);
        safe.put("x", "100");
        safe.put("y", "200");
        System.out.println(safe.get("x"));
    }
}

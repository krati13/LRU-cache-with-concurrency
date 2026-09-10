package com.example.lru;

import java.util.Objects;
import java.util.Optional;

/**
 * Simple synchronized wrapper that makes LRUCache thread-safe by synchronizing public operations.
 * Note: get mutates internal state (recency) so it requires synchronization too.
 */
public class ThreadSafeLRUCache<K, V> {
    private final LRUCache<K, V> delegate;

    public ThreadSafeLRUCache(int capacity) {
        this.delegate = new LRUCache<>(capacity);
    }

    public synchronized Optional<V> get(K key) {
        Objects.requireNonNull(key, "key must not be null");
        return delegate.get(key);
    }

    public synchronized void put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");
        delegate.put(key, value);
    }

    public synchronized int size() { return delegate.size(); }
    public int capacity() { return delegate.capacity(); }

    @Override
    public synchronized String toString() { return delegate.toString(); }
}

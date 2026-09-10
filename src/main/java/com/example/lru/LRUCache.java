package com.example.lru;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Simple LRU cache with fixed capacity.
 * - O(1) get/put using HashMap + doubly-linked list
 * - Null keys/values are NOT allowed (NullPointerException on put/get null)
 * - get(K) returns Optional.empty() for missing keys
 * - Not thread-safe; use ThreadSafeLRUCache for a synchronized wrapper
 */
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head; // most-recent
    private final Node<K, V> tail; // least-recent

    private static final class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K k, V v) { key = k; value = v; }
    }

    public LRUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Retrieve a value for key. Returns Optional.empty() if missing.
     * Moves the key to most-recent position when present (mutates cache).
     */
    public Optional<V> get(K key) {
        Objects.requireNonNull(key, "key must not be null");
        Node<K, V> node = map.get(key);
        if (node == null) return Optional.empty();
        moveToFront(node);
        return Optional.of(node.value);
    }

    /**
     * Put a key/value into the cache. If key exists, update value and recency.
     * If capacity exceeded, evict LRU entry.
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");
        Node<K, V> node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        addToFront(newNode);
        if (map.size() > capacity) removeLRU();
    }

    private void moveToFront(Node<K, V> node) {
        removeNode(node);
        addToFront(node);
    }

    private void addToFront(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private void removeLRU() {
        Node<K, V> lru = tail.prev;
        if (lru == head) return; // empty
        removeNode(lru);
        map.remove(lru.key);
    }

    public int size() { return map.size(); }
    public int capacity() { return capacity; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LRUCache[capacity=").append(capacity).append(", size=").append(size()).append("] { ");
        Node<K, V> cur = head.next;
        while (cur != tail) {
            sb.append(cur.key).append('=').append(cur.value);
            cur = cur.next;
            if (cur != tail) sb.append(", ");
        }
        sb.append(" }");
        return sb.toString();
    }
}

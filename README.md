LRU Cache (Java 21) - Interview LLD

Features
- Fixed capacity LRU cache
- O(1) get/put using HashMap + doubly-linked list
- get(key) -> Optional<V> (Optional.empty() when missing)
- Null keys and values are not allowed (NullPointerException on null)
- LRU eviction when capacity exceeded
- LRUCache is NOT thread-safe; ThreadSafeLRUCache provides synchronized access

Usage
- Build with your preferred Java build tool (javac for the simple demo).
- Run Main to see a usage example.

Design notes
- get mutates recency (updates LRU order). Because of that, get requires write-level synchronization for correctness when concurrent.
- If null values must be supported, adapt API to store a special sentinel or allow nulls and change semantics accordingly.

Contact
- Implemented for interview-style low-level design demonstration.

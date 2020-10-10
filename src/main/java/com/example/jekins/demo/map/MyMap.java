package com.example.jekins.demo.map;

import java.util.Set;

public interface MyMap<K, V> {
    int size();

    boolean isEmpty();

    V put(K k, V v);

    V get(K k);

    Set<Entry<K,V>> entrySet();

    interface Entry<K, V> {
        K getKey();

        V getValue();
    }
}

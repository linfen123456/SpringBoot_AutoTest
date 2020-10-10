package com.example.jekins.demo.list;

import java.util.Iterator;

public interface MyList<E> extends Iterator<E> {

    boolean add(E e);

    void add(int index, E object);

    boolean remove(E object);

    E remove(int index);

    int size();

    Object get(int index);

}

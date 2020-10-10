package com.example.jekins.demo.list;

import java.util.Arrays;
import java.util.function.Consumer;

public class MyArrayList<E> implements MyList<E> {
    /**
     * ArrayList底层保存数据的数组
     */
    private transient Object[] elementData;
    /**
     * 记录集合(数组)中元素数量
     */
    private int size;


    public MyArrayList() {
        //默认初始化10
        this(10);
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        elementData = new Object[initialCapacity];
    }

    @Override
    public boolean add(E e) {
        add(e, elementData, size);
        return true;
    }

    @Override
    public void add(int index, E e) {
        //检查下标是否越界
        rangeCheck(index);
        final int s;
        Object[] elementData;
        if ((s = this.size) == (elementData = this.elementData).length) {
            //如果size == 数组长度，扩容
            elementData = grow(size + 1);
        }
        System.arraycopy(elementData, index, elementData, index + 1, s - index);
        elementData[index] = e;
        size += 1;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public E remove(int index) {
        //检查是否越界
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        //删除元素
        if ((size - 1) > index) {
            System.arraycopy(elementData, index + 1, elementData, index, size - 1 - index);
            //将最后一个元素置为null
            elementData[size - 1] = null;
            size--;
        }
        return oldValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object get(int index) {
        //检查下标是否越界
        rangeCheck(index);
        return elementData[index];
    }

    //add方法
    public void add(Object o, Object[] elementData, int size) {
        if (elementData.length == size) {
            //扩容
            elementData = grow(size + 1);
        }
        elementData[size] = o;
        this.size += 1;
    }

    /**
     * 扩容
     *
     * @param minCapacity 最小容量
     * @return
     */
    public Object[] grow(int minCapacity) {
        return Arrays.copyOf(elementData, newCapacity(minCapacity));
    }

    //根据最小容量指定扩容策略(数量)
    public int newCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); //在原来容量基础上增长1.5倍
        if ((newCapacity - oldCapacity) < 0) {
            throw new OutOfMemoryError();//overflow
        }
        return newCapacity;
    }

    /**
     * 检查数组下标是否越界
     */
    public void rangeCheck(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("越界，length--->" + index);
        }
    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[ ");
        if (elementData.length > 0) {
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i] != null) {
                    sb.append(elementData[i] + ",");
                }
            }
            sb.replace(sb.length() - 1, sb.length(), " ]");
        }
        return sb.toString();
    }
}

package com.example.jekins.demo.list;

import java.util.HashMap;
import java.util.Map;

public class MyLinkedList<E> implements MyList<E> {
    transient int size;
    /**
     * 指向第一个节点的指针
     */
    transient Node<E> first;
    /**
     * 指向后一个节点的指针
     */
    transient Node<E> last;

    public MyLinkedList() {
    }

    /**
     * 节点内部类（元素值为item，包含两个指针域分别指向上一个node和下一个node）
     *
     * @param <E>
     */
    private static class Node<E> {
        /**
         * 元素
         */
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 链接e作为最后一个元素
     */
    void linkLast(E e) {
        //临时变量 记录上一个元素last
        final Node<E> l = last;
        //新建节点，让新节点prev指向last
        final Node<E> newNode = new Node<E>(l, e, null);
        //每次将新元素赋给last
        last = newNode;
        //如果是第一次新增，将新节点赋给first
        if (l == null)
            first = newNode;
            //否则，将上一个元素的next指向新节点
        else
            l.next = newNode;
        size++;
    }

    @Override
    public void add(int index, E e) {
        checkElementIndex(index);
        if (index == size)
            linkLast(e);
        else
            //传入 node(index) 获取插入位置的元素
            linkBefore(e, node(index));
    }

    /**
     * 在非空节点 succ 前插入元素
     *
     * @param e    插入的元素
     * @param succ 定位元素(插入位置的元素)
     */
    void linkBefore(E e, Node<E> succ) {
        //获取插入位置的前一个元素
        final Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, e, succ);
        //定位元素prev为新增元素
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        }else {
            pred.next = newNode;
        }
        size++;
    }

    /**
     * 从列表中删除第一次出现此元素的项，即索引最低的
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x.next != null; x = x.next) {
                if(x.item == null){
                    unlink(x);
                    return true;
                }
            }
        } else {
            for(Node<E> x = first; x.next != null; x=x.next){
                if(x.item == o){
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 断开链接  x :要删除的节点
     */
    E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /** 判断元素index是否越界*/
    void checkElementIndex(int index) {
        if (!(index >=0 && index < size)) {
            throw new IndexOutOfBoundsException("越界 index ---> " + index);
        }
    }

    /** 获取指定位置的Node */
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
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
    public String toString() {
        StringBuffer sb = new StringBuffer("[ ");
        if (this.first != null) {
            //拼接第一个节点
            sb.append(first.item);
            Node temp = this.first.next;
            for (int i = 0; i < size - 1; i++) {
                if (temp != null) {
                    sb.append(", " + temp.item);
                    temp = temp.next;
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}

package com.example.jekins.demo.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 手写HashMap  参考博客：https://blog.csdn.net/huangshulang1234/article/details/79713303
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements MyMap<K, V> {
    /**
     * 默认初始容量 aka 16   00000001 --> 00010000
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 默认加载因子（构造函数为指定时使用）
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 初始容量
     */
    private int defaultInitialCapacity;
    /**
     * 加载因子
     */
    private float defaultLoadFactor;

    /**
     * Map中Entry数量
     */
    private int size;

    /**
     * 数组(核心)
     */
    private Entry<K, V>[] table;

    /**
     * TODO key的Set集合
     */
    transient Set<MyMap.Entry<K, V>> entrySet;

    /**
     * 构造方法(“门面模式” 这里的2个构造方法其实指向的是同一个，但是对外却暴露了2个“门面”！)
     */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int defaultInitialCapacity, float defaultLoadFactor) {
        if (defaultInitialCapacity < 0) {
            throw new IllegalArgumentException("Illegal inital capacity: " + defaultInitialCapacity);
        }
        if (defaultInitialCapacity <= 0 || Float.isNaN(defaultLoadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + defaultLoadFactor);
        }
        this.defaultInitialCapacity = defaultInitialCapacity;
        this.defaultLoadFactor = defaultLoadFactor;

        //初始Entry数组
        this.table = new Entry[defaultInitialCapacity];
    }


    /**
     * HashMap的要素之一，单链表的体现就在这里
     */
    class Entry<K, V> implements MyMap.Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry() {
        }

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object obj) {
            //TODO
            return super.equals(obj);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 思路：1. 根据Key生成Hash值(Entry数组的索引，关键)
     * 存在：比较新增key与该元素的key（==）；若不等，继续比较新增key与原来的key（equals方法）
     * 相等：覆盖原来的value,返回原来的value
     * 不相等(hash值相同)：新建Entry,将原索引处的Entry放入新Entry的next中，形成链表
     *
     * @param k
     * @param v
     * @return
     */
    @Override
    public V put(K k, V v) {
        V oldValue = null;
        //判断是否需要扩容，若扩容肯定需重新散列 resize()+rehash()
        if (size >= defaultInitialCapacity * defaultLoadFactor) {
            resize(2 * defaultInitialCapacity);
        }
        //TODO 得到Hash值，计算出数组中的位置(关键，决定是否产生链表)
        int index = hash(k) & (defaultInitialCapacity - 1);
        //情况一： Entry数组中，索引index处为空,则赋值 (根据hash值判断此索引是否有元素，每次存放的位置取决于hash值)
        if (table[index] == null) {
            table[index] = new Entry<K, V>(k, v, null);
            ++size;
        } else {//情况二：若存在，需要遍历单链表
            Entry<K, V> entry = table[index];
            Entry<K, V> e = entry;
            while (e != null) {
                //情况1：先比较key地址之是否相等，若不等则equals方法比较key
                if (k == e.getKey() || k.equals(e.getKey())) {
                    oldValue = e.value;
                    //key相等，当value则覆盖原来的value，并返覆盖的元素
                    e.value = v;
                    return oldValue;
                }
                //获取单链表下一个Entry,赋给临时遍历e,继续遍历
                e = e.next;
            }
            //情况2：key不相等，hash值相等 ,此索引位置放新建的Entry,并将原索引的Entry放入next中形成链表
            table[index] = new Entry<K, V>(k, v, entry);
            ++size;
        }
        return oldValue;
    }

    @Override
    public V get(K k) {
        //根据key计算hash值
        int index = hash(k) & (defaultInitialCapacity - 1);
        if (table[index] == null) {
            return null;
        } else {//如果不为空，注意遍历链表
            Entry<K, V> entry = table[index];
            do {
                //判断key是否相等，相等返回值(do...while循环至少保证执行一次)
                if (k == entry.getKey() || k.equals(entry.getKey())) {
                    return entry.value;
                }
                //将下一个entry赋给临时变量entry
                entry = entry.next;
            } while (entry != null);
        }
        return null;
    }

    //TODO 获取key的Set集合
    @Override
    public Set<MyMap.Entry<K, V>> entrySet() {
        return null;
    }

    /**
     * 重构Entry数组
     */
    private void resize(int i) {
        Entry[] newTable = new Entry[i];
        defaultInitialCapacity = i;
        size = 0;
        rehash(newTable);
    }

    /**
     * 重构hash(将所有元素重新put)
     */
    private void rehash(Entry<K, V>[] newTable) {
        //得到原来老的Entry集合,注意遍历单链表
        List<Entry<K, V>> entryList = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                do {
                    entryList.add(entry);
                } while (entry != null);
            }
        }

        //覆盖旧的引用，赋值为扩容后的空table数组
        if (newTable.length > 0) {
            table = newTable;
        }

        //所谓重新HASH，就是重新put Entry 到 HashMap
        //这里可以看出，对于HashMap而言，如果频繁进行resize/rehash操作，是会影响性能的。
        //resize/rehash的过程，就是数组变大，原来数组中的entry元素一个个的put到新数组的过程，需要注意的是一些状态变量的改变。
        for (Entry<K, V> entry : entryList) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Hash函数
     */
    final int hash(K k) {
//        int hashCode = k.hashCode();
//        hashCode = (hashCode >>> 20) ^ (hashCode >>> 12);
//        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);  //注释的算法导致，每次得到的索引的都是0，索引相同导致产生链表
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                sb.append(table[i].toString() + ", ");
            }
        }
        sb.replace(sb.length() - 2, sb.length() - 1, "]");
        return sb.toString();
    }
}

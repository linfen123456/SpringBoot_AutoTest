package com.example.jekins.demo.test;

import com.example.jekins.demo.map.MyHashMap;
import com.example.jekins.demo.map.MyMap;

/**
 * MyHashMap测试
 */
public class MyHashMapTest {
    public static void main(String[] args) {
        MyMap<String, String> map = new MyHashMap();
        System.out.println("------------------测试put(K k,V v)-----------------------");
        map.put("p1", "张三");
        map.put("p2", "李四");
        map.put("p3", "王五");
        System.out.println("size: " + map.size());

        System.out.println("------------------测试get(K k)-----------------------");
        System.out.println("p1: " + map.get("p1"));
        System.out.println(map);
    }
}

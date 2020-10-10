package com.example.jekins.demo.test;

import com.example.jekins.demo.list.MyLinkedList;
import com.example.jekins.demo.list.MyList;

public class MyLinkedListTest {
    public static void main(String[] args){
        System.out.println("-------------------- 测试add()------------------------");
        MyList list = new MyLinkedList();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        System.out.println(list);

        System.out.println("-------------------- 测试add(int index,E e)------------------------");
        list.add(1,"插队");
        System.out.println(list);

        System.out.println("-------------------- 测试remove()------------------------");
        list.add("张三");
        System.out.println("移除前："+list);
        list.remove("张三");
        System.out.println("移除后："+list);
        list.remove(2);
        System.out.println("移除index2: "+list);

    }
}

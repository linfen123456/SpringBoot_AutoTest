package com.example.jekins.demo.test;

import java.util.LinkedList;

/**
 * LinkedList测试
 */
public class LinkedListTest {
    public static void main(String[] args){
        LinkedList<String> list = new LinkedList();
        for(int i=0;i<=10;i++){
            list.add(i+"");
        }
        list.add("0");
        System.out.println( list + "  size: "+  list.size());
        list.remove("");
        System.out.println(list);
    }


}

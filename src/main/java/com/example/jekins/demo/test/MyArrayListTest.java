package com.example.jekins.demo.test;

import com.example.jekins.demo.list.MyArrayList;
import com.example.jekins.demo.list.MyList;

public class MyArrayListTest {
    public static void main(String[] args){
//        String[] srcArr = {"1","2","3","4"};
//        String[] destArr = {"a","b","c","d","b","d"};
//        System.arraycopy(srcArr,1,destArr,3,2);
//        for(int i =0;i<destArr.length;i++){
//            System.out.println(destArr[i]);
//        }

        System.out.println("------------------------ 测试add()方法 ---------------------");
        MyList<String> myList = new MyArrayList();
        myList.add("张三");
        myList.add("李四");
        myList.add("王五");
        System.out.println(myList+" size: "+myList.size());
        myList.add(1,"大哥");
        System.out.println(myList +" size: "+myList.size());

        System.out.println("------------------------ 测试remove(int index)方法 ---------------------");
        myList.remove(1);
        System.out.println(myList +" size: "+myList.size());

        System.out.println("------------------------ 测试add(int index, E e)方法 ---------------------");

        myList.add(1,"小白");
        System.out.println(myList +" size: "+myList.size());


    }

}

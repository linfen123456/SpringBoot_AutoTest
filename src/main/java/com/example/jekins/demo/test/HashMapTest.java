package com.example.jekins.demo.test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    public static void main(String[] args){
        Map<String,String> map = new HashMap();
        String result = map.put("a","A");
        System.out.println("result: "+result +" size: "+map.size());
        map.put("b","B");

        System.out.println(map);
    }

}

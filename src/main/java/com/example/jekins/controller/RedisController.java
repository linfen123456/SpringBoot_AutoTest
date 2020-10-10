package com.example.jekins.controller;

import com.alibaba.fastjson.JSON;
import com.example.jekins.entity.User;
import com.example.jekins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    @Transactional
    public User save(User user) {
        userRepository.save(user);
        return userRepository.getOne(user.getId());
    }

    @GetMapping("/findAllUser")
    public List<User> findAllUser(){
//        Object obj = redisTemplate.opsForValue().get("user:list");
//        if(obj !=null){
//            List<User> users = (List<User>) JSON.parse(obj.toString().getBytes(),List<User>);
//            return users;
//        }
        List<User> users = userRepository.findAll();
        System.out.println(users);
        // 具体使用
        redisTemplate.opsForList().leftPush("user:list", JSON.toJSONString(users));
        stringRedisTemplate.opsForValue().set("user:name", "张三");
        return users;
    }
}

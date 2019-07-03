package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.SparkService.Analyzer;
import com.example.demo.entity.Users_user;
import com.example.demo.mapper.UserMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController// return jason data
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    private final Users_user ERR_CREATE_USERS = null;
    private final String errmsg = "errmsg";
    @RequestMapping(value = "/register")

    public HashMap<String,Object> register(Users_user users_user){
        System.out.println(users_user.getPassword());
        HashMap<String,Object> resp = new HashMap<>();
        QueryWrapper<Users_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",users_user.getEmail());
        Users_user users_user1 = userMapper.selectOne(queryWrapper);
        if (users_user1 !=ERR_CREATE_USERS){
            resp.put("status",false);
            resp.put("errmsg","Your Email account has already been registered");
        }
        else{
            int success = userMapper.insert(users_user);
            if (success != 0){
                resp.put("status",true);
                resp.put("data",users_user);
            }
            else{
                resp.put("status",false);
                resp.put("errmsg","Server Error");
            }

        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("password",password);
//        List<Users_user> users_userList = userMapper.selectByMap(map);
        return resp;


    }

    @RequestMapping(value = "/login")
    public HashMap<String,Object> login(Users_user users_user){
        HashMap<String,Object> resp = new HashMap<>();
        QueryWrapper<Users_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password",users_user.getPassword())
                .eq("email",users_user.getEmail());
        Users_user users_user1 = userMapper.selectOne(queryWrapper);
        if (users_user1 !=ERR_CREATE_USERS){
            resp.put("status",true);
        }
        else{
            resp.put("status",false);
            resp.put("errmsg","Your account hasn't been registered yet");
        }
        return resp;
    }

    @RequestMapping(value = "/recommend")
    public HashMap<String,Object> recommend(HashMap<String,Object> hashMap){
//        Analyzer analyzer = new Analyzer(hashMap);
//        return analyzer.getResultHashMap();
        return Analyzer.getTestResultHashMap();
    }

}

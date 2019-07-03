package com.example.demo.controller;

import com.example.demo.dao.Users_userMapper;
import com.example.demo.entity.Users_user;
import com.example.demo.service.Users_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController// return jason data
@RequestMapping("/user")
public class UserController {

    private final Users_user ERR_CREATE_USERS = null;
    private final String errmsg = "errmsg";
    Users_userService users_usersService;
    @Autowired
    @RequestMapping(value = "/register")
    public HashMap<String,Object> register(Users_user users_user){
        HashMap<String,Object> resp = new HashMap<>();
        Users_user insertuser= Users_userService.insert(users_user);
        if (insertuser == ERR_CREATE_USERS){
            resp.put("status",false);
           // resp.put("errmsg","Server Error");
        }
        else{
            resp.put("status",true);
            resp.put("data",users_user);
        }
        return resp;
    }
    @RequestMapping(value = "/login")
    public HashMap<String,Object> register(Users_user users_user){
        HashMap<String,Object> resp = new HashMap<>();
        Users_user selectuser= Users_userService.selectByPrimaryKey(users_user);
        if (selectuser == ERR_CREATE_USERS){
            resp.put("status",false);
            resp.put("errmsg","Your account hasn't been registered yet");
        }
        else{
            resp.put("status",true);
        }
        return resp;
    }



}

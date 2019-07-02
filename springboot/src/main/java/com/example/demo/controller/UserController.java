package com.example.demo.controller;

import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController// return jason data
public class UserController {

//    @PostMapping(value = "/user/login")
//    public String login(@RequestParam("username") String username,
//                        @RequestParam("password") String password,
//                        Map<String,Object> map){
//
//            map.put("msg", "password error");
//        return "login.html";
//    }
// status: true/false
    //data:object/string
    //errmsg:return the errmsg directly
    //users login
    private UsersMapper usersMapper;
    @RequestMapping(value = "/user/login",method = RequestMethod.GET)
    public String login(Users users){
        Users users1= usersMapper.login(users);
        if (users1 !=null){
            return "success";
        }
        else
            return "error";
    }
    //users register
    @RequestMapping("/user/register")
    @Autowired
    UsersService usersService;
    public HashMap<String,Object> register(Users users){
        Users addUsers = usersService.addUsers(users);
    }
    public String addUsers(Users users){
       // ModelAndView mv =  new   ModelAndView();
        boolean flag = usersMapper.addUsers(users);
        if (flag){
            return "success";
        }
        else
            return "error";
    }
    public boolean updateUsers(Users users);
    //deleteUsers
    public boolean deleteUsers(Users users);

}

package com.example.demo.controller;

import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

//    @PostMapping(value = "/user/login")
//    public String login(@RequestParam("username") String username,
//                        @RequestParam("password") String password,
//                        Map<String,Object> map){
//
//            map.put("msg", "password error");
//        return "login.html";
//    }

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
    public String addUsers(Users users){
       // ModelAndView mv =  new   ModelAndView();
        boolean flag = usersMapper.addUsers(users);
        if (flag){
            return "success";
        }
        else
            return "error";
    }

}

package com.example.demo.service;

import com.example.demo.dao.Users_userMapper;
import com.example.demo.entity.Users_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Users_userService {
    @Autowired
    Users_userMapper users_userMapper;
    //insert newuser=register
    public Users_user insert(Users_user users_user){
        boolean success = users_userMapper.insert(users_user);
        if (!success){
            users_user = null;
        }
        return users_user;
    }
    //user login
    public Users_user selectByPrimaryKey(String email){
        Users_user success = users_userMapper.selectByPrimaryKey(email);
         return success;
    }

    public boolean updateByPrimaryKeySelective(Users_user record){
        Users_user success =users_userMapper.updateByPrimaryKeySelective(record);
         if (success == null){
             return false;
         }
         else
             return true;
    }
    //construct a fuc to use the interface and return the final result to the controller
    //please coding it down here

}

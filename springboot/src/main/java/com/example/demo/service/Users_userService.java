package com.example.demo.service;

import com.example.demo.dao.Users_userMapper;
import com.example.demo.entity.Users_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Users_user selectByPrimaryKey(Users_user users_user){
        Users_user success = users_userMapper.selectByPrimaryKey(users_user);
         return success;
    }

    public boolean updateByPrimaryKeySelective(Users_user users_user){
        Users_user success =users_userMapper.updateByPrimaryKeySelective(users_user);
         if (success == null){
             return false;
         }
         else
             return true;
    }

    public List<Users_user> findAll(){
        List<Users_user> success = users_userMapper.findAll();
        return success;
    }
    //construct a fuc to use the interface and return the final result to the controller
    //please coding it down here

}

package com.example.demo.service;

import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    UsersMapper usersMapper;
    public Users addUsers(Users users){
        boolean success = usersMapper.addUsers(users);
        if (success){
            users.setUsername("");
        }
        else {
            users = null;
        }
        return  users;
    }
}

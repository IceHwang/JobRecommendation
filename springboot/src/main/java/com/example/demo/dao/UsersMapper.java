package com.example.demo.dao;

import com.example.demo.entity.Users;

import java.util.List;

public interface UsersMapper {

    //user login
    public Users login(Users users);
    //user register
    boolean addUsers(Users users);
    //findall users
    List<Users>findAllUsers();

}
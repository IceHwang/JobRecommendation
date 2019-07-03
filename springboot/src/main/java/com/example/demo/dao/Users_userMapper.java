package com.example.demo.dao;

import com.example.demo.entity.Users_user;

import java.util.List;

public interface Users_userMapper {
    boolean deleteByPrimaryKey(Users_user users_user);

    boolean insert(Users_user users_user);

    boolean insertSelective(Users_user users_user);

    Users_user selectByPrimaryKey(Users_user users_user);

    Users_user updateByPrimaryKeySelective(Users_user users_user);

    boolean updateByPrimaryKey(Users_user users_user);

    List <Users_user> findAll();

    //the interface of spark please coding it down here
}
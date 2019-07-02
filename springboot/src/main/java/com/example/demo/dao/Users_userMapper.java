package com.example.demo.dao;

import com.example.demo.entity.Users_user;

public interface Users_userMapper {
    int deleteByPrimaryKey(String email);

    boolean insert(Users_user record);

    boolean insertSelective(Users_user record);

    Users_user selectByPrimaryKey(String email);

    Users_user updateByPrimaryKeySelective(Users_user record);

    int updateByPrimaryKey(Users_user record);

    //the interface of spark please coding it down here
}
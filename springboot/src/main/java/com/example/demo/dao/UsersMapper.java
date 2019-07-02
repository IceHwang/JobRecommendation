package com.example.demo.dao;

import com.example.demo.entity.Users;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface UsersMapper {

    @Insert("Insert INTO users" )
    //user login
    public Users login(Users users);
    //user register
    public boolean addUsers(Users users);
    //update
    public boolean updateUsers(Users users);
    //deleteUsers
    public boolean deleteUsers(Users users);
    //findall users
    List<Users>findAllUsers();

}
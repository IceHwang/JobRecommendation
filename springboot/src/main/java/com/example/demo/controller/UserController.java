package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.SparkService.Analyzer;
import com.example.demo.entity.Users_user;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.skillMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController// return jason data
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    private skillMapper skillmapper;
    private final Users_user ERR_CREATE_USERS = null;
    private final String errmsg = "errmsg";
    @RequestMapping(value = "/register")
    public HashMap<String,Object> register(Users_user users_user){
        HashMap<String,Object> resp = new HashMap<>();
        QueryWrapper<Users_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",users_user.getEmail());
        Users_user users_user1 = userMapper.selectOne(queryWrapper);
        if (users_user1 !=ERR_CREATE_USERS){
            resp.put("status",false);
            resp.put("errmsg","Your Email account has already been registered");
        }
        else{
            int success = userMapper.insert(users_user);
            if (success != 0){
                resp.put("status",true);
                //resp.put("data",users_user);
            }
            else{
                resp.put("status",true);
                resp.put("errmsg","Server Error");
            }
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("password",password);
//        List<Users_user> users_userList = userMapper.selectByMap(map);
        //            Map<String, Object> map = new HashMap<>();
//            map.put("password",password);
//            List<Skill> skilllist = skillmapper.selectByMap(map);
        return resp;
    }
    @PostMapping(value = "/login")
    @ResponseBody
    public HashMap<String,Object> login(Users_user users_user){
        System.out.println(users_user.getEmail());
        HashMap<String,Object> resp = new HashMap<>();
        QueryWrapper<Users_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password",users_user.getPassword())
                .eq("email",users_user.getEmail());
        Users_user users_user1 = userMapper.selectOne(queryWrapper);
        System.out.println(users_user.getEmail());
        if (users_user1 !=ERR_CREATE_USERS){
            resp.put("status",true);
        }
        else{
            resp.put("status",true);
            resp.put("errmsg","Your account hasn't been registered yet");
        }
        return resp;
    }
    @RequestMapping(value = "/recommend")
    public HashMap<String,Object> recommend(HashMap<String,Object> hashMap){
//        Analyzer analyzer = new Analyzer(hashMap);
//        return analyzer.getResultHashMap();
        return Analyzer.getTestResultHashMap();
    }




    @ResponseBody
    @RequestMapping(value = "/config")
    public HashMap<String,Object> config(@RequestParam("file") MultipartFile inputFile) throws IOException {
        HashMap<String,Object> resp = new HashMap<>();

        if (inputFile==null||inputFile.getSize()==0)
        {
            resp.put("status",false);
            return resp;
        }

        File outputFile = new File("../data/"+inputFile.getOriginalFilename());
        inputFile.transferTo(outputFile);
        // 读取文件第一行
        BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
        System.out.println(bufferedReader.readLine());
        // 输出绝对路径
        System.out.println(outputFile.getAbsolutePath());
        bufferedReader.close();

        resp.put("status",true);
        return resp;


    }


}

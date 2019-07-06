package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.SparkService.Analyzer;
import com.example.demo.entity.MyMap;
import com.example.demo.entity.Users_user;
import com.example.demo.mapper.UserMapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller// return jason data
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    private final Users_user ERR_CREATE_USERS = null;
    private final String errmsg = "errmsg";

    @PostMapping(value = "/register")
    @ResponseBody
    public HashMap<String,Object> register(Users_user users_user){
        System.out.println(users_user.getPassword());
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
    public HashMap<String,Object> login(Users_user users_user, HttpServletRequest request){
        System.out.println(users_user.getPassword());
        HashMap<String,Object> resp = new HashMap<>();
        QueryWrapper<Users_user> testAdmin= new QueryWrapper<>();
        testAdmin.eq("email",users_user.getEmail()).eq("admin",1);
        Users_user users_user1 = userMapper.selectOne(testAdmin);
        if(users_user1 != null){
            users_user.setAdmin(1);
        }
        QueryWrapper<Users_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",users_user.getEmail());
        Users_user users_user2 = userMapper.selectOne(queryWrapper);
        if(users_user2 == ERR_CREATE_USERS){
            resp.put("status",false);
            resp.put("errmsg","Your account hasn't been registered yet");
        }else{
            queryWrapper.eq("password",users_user.getPassword());
            users_user2 = userMapper.selectOne(queryWrapper);
            if (users_user2 == ERR_CREATE_USERS){
                resp.put("status",false);
                resp.put("errmsg","Wrong Password");
            }else
            {
                resp.put("status",true);
                HttpSession session=request.getSession();
                session.setAttribute("email",users_user.getEmail());
                session.setAttribute("password",users_user.getPassword());
                if (users_user.getAdmin() ==1)
                {
                    session.setAttribute("admin","1");
                    resp.put("admin",true);

                }else
                {
                    session.setAttribute("admin","0");
                    resp.put("admin",false);
                }
            }
        }

        return resp;
    }



    @RequestMapping(value = "/config")
    public String config(HttpServletRequest request){
        HttpSession session=request.getSession();
        Object object=session.getAttribute("admin");
        String admin=(String) object;
        if (admin==null)
            return null;
        else if(!admin.equals("1"))
            return null;
        else
            return "config";
    }


    @ResponseBody
    @RequestMapping(value = "/upload")
    public HashMap<String,Object> upload(@RequestParam("file") MultipartFile inputFile,HttpServletRequest request) throws IOException {
        HttpSession session=request.getSession();
        Object object=session.getAttribute("admin");
        String admin=(String) object;
        if (admin==null)
            return null;
        else if(!admin.equals("1"))
            return null;

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

    @RequestMapping(value = "/userhome")
    public String userhome(HttpServletRequest request){
        HttpSession session=request.getSession();
        Object object=session.getAttribute("email");
        String emaill=(String) object;
        if (emaill==null)
            return "error";
        else
            return "userhome";
    }


    @ResponseBody
    @RequestMapping(value = "/get_info")
    public HashMap<String,Object> get_info(HttpServletRequest request)
    {
        HashMap<String,Object> resp = new HashMap<>();
        HttpSession session=request.getSession();
        Object object=session.getAttribute("email");
        String emaill=(String) object;
        if (emaill==null)
        {
            resp.put("status",false);
            return resp;
        }
        resp.put("status",true);
        ArrayList<String> list=Analyzer.getJobList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("/"))
                list.remove(i);

        }
        resp.put("jobs",list.toArray());
        resp.put("skills",Analyzer.getSkillList().toArray());

        return resp;


    }

    @ResponseBody
    @RequestMapping(value = "/choose")
    public HashMap<String,Object> choose(MyMap myMap, HttpServletRequest request)
    {

        HashMap<String,Object> resp=new HashMap<>();
        HttpSession session=request.getSession();
        Object object=session.getAttribute("email");
        String emaill=(String) object;
        if (emaill==null)
        {
            resp.put("status",false);
            return resp;
        }
        ArrayList<String> skillList=myMap.getSkillList();
        String s=skillList.get(0);
        skillList.set(0,s.substring(1,s.length()));
        s=skillList.get(skillList.size()-1);
        skillList.set(skillList.size()-1,s.substring(0,s.length()-1));
        for (int i = 0; i < skillList.size(); i++) {
            String str=skillList.get(i);
            skillList.set(i,str.substring(1,str.length()-1));
        }
        session.setAttribute("preferedJob",myMap.getPreferedJob());
        session.setAttribute("skillList",skillList);

        resp.put("status",true);
        return resp;


    }


    @RequestMapping(value = "/recommend")
    public String recommend(HttpServletRequest request){
        HttpSession session=request.getSession();
        Object object=session.getAttribute("email");
        String emaill=(String) object;
        if (emaill==null)
            return "index.html";
        else
            return "recommend";
    }

    @RequestMapping(value = "/get_recommend")
    @ResponseBody
    public HashMap<String,Object> get_recommend(HttpServletRequest request){
        HttpSession session=request.getSession();
        Object object=session.getAttribute("email");
        String emaill=(String) object;
        if (emaill==null)
            return null;
        else
        {
            HashMap<String,Object> hashMap=new HashMap<>();
            HashMap<String,Object> resp=new HashMap<>();
            Object object1=session.getAttribute("preferedJob");
            Object object2=session.getAttribute("skillList");
            if (object1==null||object2==null)
            {
                resp.put("status",false);
                return resp;
            }
            hashMap.put("preferedJob",object1);
            hashMap.put("skillList",object2);
            session.removeAttribute("preferedJob");
            session.removeAttribute("skillList");

            Analyzer analyzer = new Analyzer(hashMap);
            resp=analyzer.getResultHashMap();
            resp.put("status",true);
            return resp;
        }

    }


}

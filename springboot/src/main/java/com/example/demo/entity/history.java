package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("history")
public class history {
    @TableId("id")
    private int id;
    private int user_id;
    private String time;
    private String info;

    public history(){
        super();
    }

    public history(int user_id){
        this.user_id = user_id;
    }

    public history(int user_id,String time,String info){
        this.user_id = user_id;
        this.time = time;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

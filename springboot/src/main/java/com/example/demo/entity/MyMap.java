package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class MyMap {
    private String preferedJob;
    private ArrayList<String> skillList;

    public MyMap(String preferedJob,ArrayList<String> skillList)
    {
        this.preferedJob=preferedJob;
        this.skillList=skillList;
    }

    public void setPreferedJob(String preferedJob) {
        this.preferedJob = preferedJob;
    }

    public ArrayList<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<String> skillList) {
        this.skillList = skillList;
    }

    public String getPreferedJob() {
        return preferedJob;
    }
}

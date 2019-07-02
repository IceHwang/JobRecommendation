package com.example.Controller;

import com.example.SparkService.FPTreeAnalyzer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping("/register")
    public String Login() {

        String result = FPTreeAnalyzer.run();
        return result;


    }
}

package com.halfopen.learn.controller;

/**
 * Created by h on 2017/8/7.
 */


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/My")
@Controller
public class MyController{

    @RequestMapping("/hello")
    public String printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");

        return "hello";
    }

    //访问地址：http://localhost:8080/Test/returnSuccess
    @RequestMapping(value = "returnSuccess")    //实际访问的url地址
    public String returnSuccess() {
        return "/Views/success";    //返回Views文件夹下的success.jsp页面
    }


    @RequestMapping(value = "returnString", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody       //用于返回字符串,不写即返回视图
    public String returnString() {
        return "hello return string 这是中文，并没有乱码";
    }
}

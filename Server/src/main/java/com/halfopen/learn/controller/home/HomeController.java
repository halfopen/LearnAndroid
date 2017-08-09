package com.halfopen.learn.controller.home;


import com.halfopen.learn.base.BaseController;
import com.halfopen.learn.controller.ResultBuilder;
import com.halfopen.learn.model.User;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/")
@Controller
public class HomeController extends BaseController{

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        model.addAttribute("uername", username);
        return "index";

    }
}
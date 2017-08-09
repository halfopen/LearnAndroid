package com.halfopen.learn.controller.user;

import com.halfopen.learn.base.Constant;
import com.halfopen.learn.controller.ResultBuilder;
import com.halfopen.learn.base.BaseController;
import com.halfopen.learn.model.User;
import com.halfopen.learn.service.UserService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by h on 2017/8/7.
 */

@RequestMapping("/user")
@Controller
public class UserController extends BaseController{
    @Resource
    UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, ModelMap model) {
        if(request.getMethod().equals("GET")) {
//            log.debug("login");
//            log.error("fuck");
//            log.info("fuckinfo");
            return "user/login";
        }else{
            return "hello";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public String login(HttpServletRequest request, @RequestParam String name,@RequestParam String password) throws IOException {
        System.out.println(name+" "+password);

        ResultBuilder rb = new ResultBuilder("1", "", "");

        System.out.println(userService);

        HttpSession session = request.getSession();

        User usobj = userService.getUser(name, password);
        ObjectMapper objectMapper = new ObjectMapper();
        if (usobj!=null){
            System.out.println(usobj);
            rb.setResult(Constant.SUCCESS);
            rb.setData(usobj.toString());
            //rb.setMessage("登录成功");
            session.setAttribute("username",usobj.getName());
            log.info("mylog:user "+usobj.getName()+" login");
        }else{
            rb.setResult(Constant.FAIL);
            //rb.setMessage("用户不存在或密码错误");
        }

        String jsonString = objectMapper.writeValueAsString(rb);

        return jsonString;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String logout(HttpServletRequest request, @RequestParam String name) throws IOException {

        ResultBuilder rb = new ResultBuilder("1", "success", "");
        System.out.println(userService);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        ObjectMapper objectMapper = new ObjectMapper();
        if (username!=null && username.equals(name)){
            //rb.setMessage("注销成功");
            log.info("mylog:user:"+name+" logout");
            session.removeAttribute("username");
        }else{
            rb.setResult("-1");
            //rb.setMessage("用户不存在或密码错误");
        }
        String jsonString = objectMapper.writeValueAsString(rb);
        return jsonString;
    }

    @RequestMapping(value = "/log")
    @ResponseBody
    public String savelog(@RequestParam String logMessage){
        ResultBuilder rb = new ResultBuilder("1", "", "");

        System.out.println(logMessage);

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("mylog:"+logMessage);

        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(rb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}

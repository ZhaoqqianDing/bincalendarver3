package com.example.websitedemo.Controller;




import com.example.websitedemo.Common.CommonUtils;
import com.example.websitedemo.Common.MD5;
import com.example.websitedemo.entity.User;
import com.example.websitedemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller


@RequestMapping("/UserController")
@Slf4j
@Api(value = "UserController", description = "加载用户列表")

public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser/{id}")
    public String GetUser(@PathVariable int id){
        return userService.Sel(id).toString();
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserName", value = "userName", required = true, dataType = "String"),
            @ApiImplicitParam(name = "Password", value = "password", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ConfirmPassword", value = "confirmPassword", required = true, dataType = "String"),

    })

    @RequestMapping("/register")
    public String register(HttpServletRequest request){
        String userName = CommonUtils.trim(request.getParameter("UserName"));
        String password=CommonUtils.trim(request.getParameter("Password"));
        log.info("用户名及其密码： "+userName+" "+password);
        String confirmPassword=CommonUtils.trim(request.getParameter("ConfirmPassword"));
        if("".equals(userName)||"".equals(password)||!password.equals(confirmPassword)){
          log.info("参数错误");
          return "register";
          //todo check userName is unique
        }

        User user = new User();
        user.setUserName(userName);
        try{
            user=userService.selectUserByUserName(userName);
            if(user!=null){
                return "register";
            }
        }
        catch (Exception e){
            log.info("find the ");
        }
        user=new User();
        user.setUserName(userName);
        MD5 md5 = new MD5();
        String encryptPassword = md5.start(password);
        user.setPassword(encryptPassword);
        int su = 0;
        try{
            su = userService.register(user);
        }
        catch (Exception e){
            log.info(""+e);

        }
        if(su==0) {
            log.info("注册失败");

        }
        log.debug(userName+" register successful");
        request.getSession().setAttribute("session_user",user);
        //todo I do not understand why the session can not work, here I just use the log in session
        return "logIn";

    }

    //跳转注册页
    @RequestMapping("/toRegister")
    public String toRegister(){
        log.info("进入界面");
        return "register";
    }

    @RequestMapping("/toLogIn")
    public String toLogIn(){
        log.info("进入界面");
        return "logIn";
    }

    //测试未登陆拦截页面
    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("user",user);
        System.out.println(user.getUserName()+" 123");
        return "welcome";
    }

    @RequestMapping("/test")
    public void test(Model model){
       User user= (User)model.getAttribute("user");


    }

    @RequestMapping("/toCalendar")
    public String toCalendar(){

        return "calendar";
    }

    //退出登录
    @RequestMapping("/outUser")
    public void outUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("session_user");
        request.getSession().removeAttribute("calendar");

        response.sendRedirect("/UserController/toIndex");
    }

    @RequestMapping("/toIndex")
    public String show(){
        return "index";
    }

    //登录操作

    @RequestMapping("/loginUser")
    public String login(User user, HttpServletRequest request){
        String userName = user.getUserName();
        String passWord = user.getPassword();
        MD5 md5= new MD5();
        passWord=md5.start(passWord);
        User loginUser =userService.login(userName,passWord);
        if (loginUser==null){
            return "用户名或密码错误";
        }else{
//            try{
//                ul=userService.selectUserByUserName(userName);
//            }
//            catch (Exception e){
//                log.info(e+"");
//            }
            request.getSession().setAttribute("session_user",loginUser);//登录成功后将用户放入session中，用于拦截
            return "welcome";
        }

    }
    @RequestMapping("/getUserName")
    public String getUserName(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("userName",user.getUserName());
        return user.getUserName();
    }

    @ResponseBody
    @RequestMapping("/verifyUniqueUser")
    public int verifyUniqueUser(HttpServletRequest request){
        String userName = CommonUtils.trim(request.getParameter("UserName"));
        int isUnique =0;
        try{
            User user = userService.selectUserByUserName(userName);
            if(user!=null){
                isUnique=1;
            }
        }
        catch (Exception e){
            log.info(e+"");
        }
        return isUnique;
    }

}
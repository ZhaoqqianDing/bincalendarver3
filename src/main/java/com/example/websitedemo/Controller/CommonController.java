package com.example.websitedemo.Controller;

import com.example.websitedemo.Common.CommonUtils;
import com.example.websitedemo.entity.User;
import com.example.websitedemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
@RequestMapping("/CommonController")
public class CommonController {
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping("/verifyUniqueUser")
    public int verifyUniqueUser(HttpServletRequest request){
        String userName = CommonUtils.trim(request.getParameter("UserNameR"));
        log.info("try to search the User: " +userName);
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
        log.info("unique or not: "+isUnique);
        return isUnique;
    }
}

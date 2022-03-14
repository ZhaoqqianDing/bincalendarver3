package com.example.websitedemo.serviceImpl;




import com.example.websitedemo.Common.CommonUtils;
import com.example.websitedemo.entity.Calendar;
import com.example.websitedemo.entity.User;
import com.example.websitedemo.map.CalendarMapper;
import com.example.websitedemo.map.UserMapper;
import com.example.websitedemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    CalendarMapper calendarMapper;
    @Override
    public User Sel(int id){
        return userMapper.Sel(id);
    }
    public User login(String userName, String password) {
        return userMapper.login(userName,password);
    }

    public int register(User user) {
        String userId =System.currentTimeMillis()+ CommonUtils.generateNumbers(6);
        String calendarID =System.currentTimeMillis()+ CommonUtils.generateNumbers(6);
        user.setUserId(userId);
        user.setUserType("normal");
        int createUser=0;
        int createCalendar=0;
        try{
           createUser= userMapper.register(user);
           createCalendar=calendarMapper.createCalendar(calendarID,userId);
        }
        catch (Exception e){
            log.info(e+"");

        }
        if(createCalendar*createUser==0){
            return 0;
        }
        else{
            return 1;
        }
    }

    public User selectUserByUserName(String userName){
        log.info("try to get the User");
        User user = new User();
        try{
          user = userMapper.selectUserByUserName(userName);
        }
        catch (Exception e){
            log.info("failed to get the User: "+e);
        }
        return user;
    }

}
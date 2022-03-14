package com.example.websitedemo.map;




import com.example.websitedemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserMapper {

    User Sel(int id);

    User login(String userName,String passWord);
    User selectUserByUserName(String userName);
    int register(User user);
}

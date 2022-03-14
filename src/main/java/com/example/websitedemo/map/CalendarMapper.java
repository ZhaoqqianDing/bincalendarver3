package com.example.websitedemo.map;

import com.example.websitedemo.entity.Calendar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CalendarMapper {
    int createCalendar(String calendarID,String userID);
    Calendar getCalendarByUserID(String userID);
}

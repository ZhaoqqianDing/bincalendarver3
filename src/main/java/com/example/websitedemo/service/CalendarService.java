package com.example.websitedemo.service;

import com.example.websitedemo.entity.Benefit;
import com.example.websitedemo.entity.Calendar;
import com.example.websitedemo.entity.Card;

public interface CalendarService {
    int toCalendar(String UserID);
    Card getCardByUserIDTime(String userID, String curDate);
    Calendar getCalendarByUserID(String userID);
    int createCard(Card card);
    int updateCard(Card card);
    Benefit getBenefitByName(String materil);
}

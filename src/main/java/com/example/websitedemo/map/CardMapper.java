package com.example.websitedemo.map;

import com.example.websitedemo.entity.Card;

public interface CardMapper {
    Card selectCardByID(int cardID);
    int updateCardByID(Card card);
    int createCard(Card card);
    int removeCardByID(int carID);
    Card selectCardByDate(String calendarId,String curDate);
}

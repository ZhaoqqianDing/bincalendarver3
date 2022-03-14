package com.example.websitedemo.entity;

import lombok.Data;

@Data
public class Card {
    private String cardId;
    private String cardName;

    private String calendarId;
    private String type;
    private String weight;
    private String description;
    private String startTime;
    private String endTime;

}

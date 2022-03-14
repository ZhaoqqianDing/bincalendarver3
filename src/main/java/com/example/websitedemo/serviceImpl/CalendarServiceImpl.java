package com.example.websitedemo.serviceImpl;

import com.example.websitedemo.entity.Benefit;
import com.example.websitedemo.entity.Calendar;
import com.example.websitedemo.entity.Card;
import com.example.websitedemo.map.BenefitMapper;
import com.example.websitedemo.map.CalendarMapper;
import com.example.websitedemo.map.CardMapper;
import com.example.websitedemo.service.CalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CalendarServiceImpl implements CalendarService {
    @Resource
    CalendarMapper calendarMapper;
    @Resource
    CardMapper cardMapper;
    @Resource
    BenefitMapper benefitMapper;
    @Override
    public int toCalendar(String userID){
        return 0;
    }
    @Override
    public Card getCardByUserIDTime(String calendarID, String curDate){
        log.info("try to find this card");
        Card card = new Card();
        try{
            card = cardMapper.selectCardByDate(calendarID,curDate);
        }
        catch (Exception e){
            log.info(e+" do not find the card");
        }
        return card;
    }
    @Override
    public Calendar getCalendarByUserID(String userID){
        log.info("start to get the calendar");
        Calendar calendar = new Calendar();
        try{
            calendar= calendarMapper.getCalendarByUserID(userID);
        }
        catch (Exception e){
            log.info(e+" the calendar are not found");
        }
        return calendar;
    }
    @Override
    public int createCard(Card card){
        log.info("try to create the card "+card.getCardId() );
        int createCardSuccess =0;
        try{
            createCardSuccess= cardMapper.createCard(card);
        }
        catch (Exception e){
            log.info(e+" create card failed");
        }
        return createCardSuccess;

    }
    @Override
    public int updateCard(Card card){
        log.info("try to update the card");
        int updateCardSuccess =0;
        try{
            updateCardSuccess=cardMapper.updateCardByID(card);
        }
        catch (Exception e){
            log.info("update card failed "+card.getCardId()+" "+e);
        }
        return updateCardSuccess;
    }
    public Benefit getBenefitByName(String material){
        log.info("start to get the benefit");
       Benefit benefit = new Benefit();
        try{
            benefit= benefitMapper.getBenefitByName(material);
        }
        catch (Exception e){
            log.info(e+" the benefit are not found");
        }
        return benefit;
    }
}

package com.example.websitedemo.Controller;


import com.example.websitedemo.Common.CommonUtils;
import com.example.websitedemo.entity.Benefit;
import com.example.websitedemo.entity.Card;
import com.example.websitedemo.entity.User;
import com.example.websitedemo.service.CalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@RequestMapping("/CalendarController")
@Slf4j
@Controller
public class CalendarController {
    @Autowired
    CalendarService calendarService;
    public String[] weeks = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    @RequestMapping("/toCalendar")
    public String toCalendar(HttpServletRequest request, Model model){
        log.info("enter in calendar page");
        User user = (User) request.getSession().getAttribute("session_user");
        log.info(user.getUserId()+"user Id");
        model.addAttribute("user",user);
        Card[] thisCalendar = new Card[7];

        com.example.websitedemo.entity.Calendar calendar = new com.example.websitedemo.entity.Calendar();
        try{
            calendar=calendarService.getCalendarByUserID(user.getUserId());
        }
        catch (Exception e){
            log.info(e+" CalendarController does not find the calendar");
        }
        request.getSession().setAttribute("calendar",calendar);
        log.info(calendar.getCalendarId()+" calendar ID");
        int curDay = getWeekDayNum();
        for(int i=0;i<7;i++){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i-curDay);
            String theDate = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);

            Card theCard = calendarService.getCardByUserIDTime(calendar.getCalendarId()+"",theDate);
            if(theCard!=null){
                thisCalendar[i]=theCard;
            }
            else{
                Card card = new Card();
                String cardDate =  c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
                card.setStartTime(cardDate);
                card.setEndTime(cardDate);
                card.setType("");
                card.setDescription("");
                card.setCardId(System.currentTimeMillis()+ CommonUtils.generateNumbers(6));
                card.setCardName("");
                card.setWeight("0");
                card.setCalendarId(calendar.getCalendarId());
                int createCardSuccess =0;
                try{
                    createCardSuccess=calendarService.createCard(card);
                }
                catch (Exception e){
                    log.info("create card failed "+e);
                }
                thisCalendar[i]=card;
            }



        }
        int total=0;
        double[] saveNum = new double[3];//Co2 water tree
        for(int i=0;i<7;i++){
            if(thisCalendar[i].getType()!=null && !thisCalendar[i].getType().equals("")){
                Benefit benefit = new Benefit();
                try{
                    benefit= calendarService.getBenefitByName(thisCalendar[i].getType());

                }
                catch (Exception e){
                    log.info("do not find the benefit "+e);
                }
                if(benefit!=null&&thisCalendar[i].getWeight()!=null && !thisCalendar[i].getWeight().equals("")){
                    int a =0;
                    try{
                        a = Integer.valueOf(thisCalendar[i].getWeight());
                        total= total+a;
                    }
                    catch (Exception e){
                        log.info("the weight is not a number");

                    }
                    saveNum[0]=benefit.getCO2()*a;
                    saveNum[1]=benefit.getWater()*a;
                    saveNum[2]=benefit.getTree()*a;
                }
            }
            else{
                continue;
            }
        }
        String prompt = "This week you dispose "+total+"kg waste.";
        String prompt2="Your working helps the governments reduce "+saveNum[0]+" kg CO2 emission";
        String prompt3="And your working is equal to plant "+saveNum[2]+" trees, and save "+saveNum[1]+" kg water!";
        model.addAttribute("prompt",prompt);
        model.addAttribute("prompt2",prompt2);
        model.addAttribute("prompt3",prompt3);

        model.addAttribute("Sunday",thisCalendar[0]);
        model.addAttribute("Monday",thisCalendar[1]);
        model.addAttribute("Tuesday",thisCalendar[2]);
        model.addAttribute("Wednesday",thisCalendar[3]);
        model.addAttribute("Thursday",thisCalendar[4]);
        model.addAttribute("Friday",thisCalendar[5]);
        model.addAttribute("Saturday",thisCalendar[6]);

        return "calendar";
    }

    @RequestMapping("/refreshCalendar")
    public String refreshCalendar(HttpServletRequest request, Model model){
        com.example.websitedemo.entity.Calendar calendar = (com.example.websitedemo.entity.Calendar) request.getSession().getAttribute("calendar");

        String cardName= CommonUtils.trim(request.getParameter("cardName"));
        String startTime = CommonUtils.trim(request.getParameter("startTime"));
        String cardID= CommonUtils.trim(request.getParameter("cardID"));
        String type = CommonUtils.trim(request.getParameter("type"));
        String weight= CommonUtils.trim(request.getParameter("weight"));
        String description = CommonUtils.trim(request.getParameter("description"));
        String calendarId= CommonUtils.trim(request.getParameter("calendarId"));
        String endTime = CommonUtils.trim(request.getParameter("endTime"));
        log.info(startTime+" weight");
        log.info(type+" type");
        Card card = new Card();
        try{
            card = calendarService.getCardByUserIDTime(calendar.getCalendarId(),startTime);
        }
        catch (Exception e){
            log.info("get card failed "+e );
        }
        if(!"".equals(weight)){
            card.setWeight(weight);
        }
        if(!"".equals(type)){
            card.setType(type);
        }
        if(!"".equals(cardName)){
            card.setCardName(cardName);
        }
        if(!"".equals(description)){
            card.setDescription(description);
        }
        try{
            calendarService.updateCard(card);
        }
        catch (Exception e){
            log.info("update Card Failed"+e);
        }
//        String[] cardDate = startTime.split("-");
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(Integer.valueOf(cardDate[0]),Integer.valueOf(cardDate[1]),Integer.valueOf(cardDate[2]));
//        int weekday=getWeekDayNum(calendar1);
//        if(weekday==0){
//            model.addAttribute("Sunday",card);
//
//        }
//       else if(weekday==1){
//            model.addAttribute("Monday",card);
//
//        }
//       else if(weekday==2){
//            model.addAttribute("Tuesday",card);
//
//        }
//       else if(weekday==3){
//            model.addAttribute("Wednesday",card);
//
//        }
//       else if(weekday==4){
//            model.addAttribute("Thursday",card);
//
//        }
//       else if(weekday==5){
//            model.addAttribute("Friday",card);
//
//        }
//        else if(weekday==6){
//            model.addAttribute("Saturday",card);
//
//        }
//        return "calendar";
        return toCalendar(request,model);
    }


    public String getWeekDay(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];

    }
    public int getWeekDayNum(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return week_index;

    }
    public int getWeekDayNum(Calendar calendar){
        int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return week_index;
    }
    @RequestMapping(value = "Sunday", method = RequestMethod.GET)
    public String messages(Model model,Card card) {
        model.addAttribute("Sunday",card);
        return "Sunday/list";
    }
    //todo openData
    public void setCardDes(Card card){
        String description=" good;bad";

    }

}

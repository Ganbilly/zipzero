package com.ktds.zipzero.all.dto;

import lombok.Data;

@Data
public class TimeDTO {
    public String year;
    public String month;
    public String date;
    public String hour;
    public String min;
    public String sec;

    public String getTime(){
        String str = year + "-" + month + "-" + date + "T" + hour + ":" + min + ":" + sec;
        return str;
    }
}
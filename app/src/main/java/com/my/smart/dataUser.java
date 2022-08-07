package com.my.smart;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dataUser {

    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String fname, cname;
    long time;
    String stime, etime,hrs, pur;

    public dataUser() {
    }

    public dataUser(String fname, String cname, long time, String stime, String etime, String hrs, String pur) {
        this.fname = fname;
        this.cname = cname;
        this.time = time;
        this.stime = stime;
        this.etime = etime;
        this.hrs = hrs;
        this.pur = pur;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTime() {
        Timestamp ts=new Timestamp(time);
        Date date=new Date(ts.getTime());


        return formatter.format(date);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getHrs() {
        return hrs;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public String getPur() {
        return pur;
    }

    public void setPur(String pur) {
        this.pur = pur;
    }
}

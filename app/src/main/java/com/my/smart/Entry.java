package com.my.smart;

public class Entry {
    String id, fname, cname,time;
    String stime, etime,hrs, pur;

    public Entry() {
    }

    public Entry(String id, String fname, String cname, String time, String stime, String etime, String hrs, String pur) {
        this.id = id;
        this.fname = fname;
        this.cname = cname;
        this.time = time;
        this.stime = stime;
        this.etime = etime;
        this.hrs = hrs;
        this.pur = pur;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return time;
    }

    public void setTime(String time) {
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
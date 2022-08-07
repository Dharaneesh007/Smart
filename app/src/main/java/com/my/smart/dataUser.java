package com.my.smart;


public class dataUser {

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

    public long getTime() {
        return time;
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

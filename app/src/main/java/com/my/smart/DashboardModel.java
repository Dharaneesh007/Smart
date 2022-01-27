package com.my.smart;

public class DashboardModel {
    String  fname, cname, date, stime, etime, hr, put;

    public DashboardModel() {
    }

    public DashboardModel( String fname, String cname, String date, String stime, String etime, String hr, String put) {

        this.fname = fname;
        this.cname = cname;
        this.date = date;
        this.stime = stime;
        this.etime = etime;
        this.hr = hr;
        this.put = put;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getPut() {
        return put;
    }

    public void setPut(String put) {
        this.put = put;
    }
}

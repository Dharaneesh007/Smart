package com.my.smart;

public class ReadWriteUserDetails {

    public String  dob, gender, mobile,role;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String role) {
        this.role = role;
    }

    public ReadWriteUserDetails(String dob, String gender, String mobile) {
        this.dob = dob;
        this.gender = gender;
        this.mobile = mobile;
    }
}

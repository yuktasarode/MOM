package com.example.priyanshi.projectmom;

/**
 * Created by Priyanshi on 17-07-2019.
 */

public class Member {

    double ans,eachdaytarget;
    public Member(){

    }

    public Member(double ans,double eachdaytarget) {
        this.ans = ans;
        this.eachdaytarget=eachdaytarget;
    }

    public double getEachdaytarget() {
        return eachdaytarget;
    }

    public void setEachdaytarget(double eachdaytarget) {
        this.eachdaytarget = eachdaytarget;
    }

    public double getAns() {
        return ans;

    }

    public void setAns(double ans) {
        this.ans = ans;
    }
}

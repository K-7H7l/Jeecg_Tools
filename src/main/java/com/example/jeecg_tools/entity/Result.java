package com.example.jeecg_tools.entity;

public class Result {
    boolean res;
    String payload;
    String vuln;

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVuln() {
        return vuln;
    }

    public void setVuln(String vuln) {
        this.vuln = vuln;
    }

    public Result(boolean res, String payload, String vuln) {
        this.res = res;
        this.payload = payload;
        this.vuln = vuln;
    }
}

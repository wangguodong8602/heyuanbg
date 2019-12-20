package com.background.vo;

import java.math.BigDecimal;

public class CommisionShow {
    private Integer id;

    private String realname;

    private String agentname;

    private String role;

    private String zfbCommision;

    private String wxCommision;

    private String ysfCommision;

    private String totalCommision;

    public CommisionShow(Integer id, String realname, String agentname, String role, String zfbCommision, String wxCommision, String ysfCommision, String totalCommision) {
        this.id = id;
        this.realname = realname;
        this.agentname = agentname;
        this.role = role;
        this.zfbCommision = zfbCommision;
        this.wxCommision = wxCommision;
        this.ysfCommision = ysfCommision;
        this.totalCommision = totalCommision;
    }

    public CommisionShow() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getZfbCommision() {
        return zfbCommision;
    }

    public void setZfbCommision(String zfbCommision) {
        this.zfbCommision = zfbCommision;
    }

    public String getWxCommision() {
        return wxCommision;
    }

    public void setWxCommision(String wxCommision) {
        this.wxCommision = wxCommision;
    }

    public String getYsfCommision() {
        return ysfCommision;
    }

    public void setYsfCommision(String ysfCommision) {
        this.ysfCommision = ysfCommision;
    }

    public String getTotalCommision() {
        return totalCommision;
    }

    public void setTotalCommision(String totalCommision) {
        this.totalCommision = totalCommision;
    }
}
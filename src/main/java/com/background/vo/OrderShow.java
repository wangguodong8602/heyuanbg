package com.background.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderShow {
    private Integer id;

    private String realname;

    private String agentname;

    private String role;

    private BigDecimal zfbAmount;

    private BigDecimal wxAmount;

    private BigDecimal ysfAmount;

    private BigDecimal totalAmount;

    public OrderShow(Integer id, String realname, String agentname, String role, BigDecimal zfbAmount, BigDecimal wxAmount, BigDecimal ysfAmount, BigDecimal totalAmount) {
        this.id = id;
        this.realname = realname;
        this.agentname = agentname;
        this.role = role;
        this.zfbAmount = zfbAmount;
        this.wxAmount = wxAmount;
        this.ysfAmount = ysfAmount;
        this.totalAmount = totalAmount;
    }

    public OrderShow() {
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

    public BigDecimal getZfbAmount() {
        return zfbAmount;
    }

    public void setZfbAmount(BigDecimal zfbAmount) {
        this.zfbAmount = zfbAmount;
    }

    public BigDecimal getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(BigDecimal wxAmount) {
        this.wxAmount = wxAmount;
    }

    public BigDecimal getYsfAmount() {
        return ysfAmount;
    }

    public void setYsfAmount(BigDecimal ysfAmount) {
        this.ysfAmount = ysfAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
package com.background.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Integer id;

    private Long orderNo;

    private String username;

    private String agentname;

    private String shoppername;

    private BigDecimal payment;

    private String payPlatform;

    private Date updateTime;

    public OrderInfo(Integer id, Long orderNo, String username, String agentname, BigDecimal payment, String payPlatform, Date updateTime, String shoppername) {
        this.id = id;
        this.orderNo = orderNo;
        this.username = username;
        this.agentname = agentname;
        this.shoppername = shoppername;
        this.payment = payment;
        this.payPlatform = payPlatform;
        this.updateTime = updateTime;
    }
    public OrderInfo() {
        super();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getShoppername() {
        return shoppername;
    }

    public void setShoppername(String shoppername) {
        this.shoppername = shoppername;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
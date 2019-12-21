package com.background.vo;

import java.util.Date;

public class DeviceInfo {
    private Integer id;

    private String deviceSn;

    private String hostname;

    private String agentname;

    private String phone;

    private String shoppername;

    private Date createTime;

    private Date updateTime;

    public DeviceInfo(Integer id, String deviceSn, String hostname, String agentname, String phone, String shoppername, Date createTime, Date updateTime) {
        this.id = id;
        this.deviceSn = deviceSn;
        this.hostname = hostname;
        this.agentname = agentname;
        this.phone = phone;
        this.shoppername = shoppername;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DeviceInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShoppername() {
        return shoppername;
    }

    public void setShoppername(String shoppername) {
        this.shoppername = shoppername;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
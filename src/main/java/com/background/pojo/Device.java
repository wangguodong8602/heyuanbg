package com.background.pojo;

import java.util.Date;

public class Device {
    private Integer id;

    private Integer userId;

    private Integer agentId;

    private String deviceId;

    private String deviceKey;

    private String deviceType;

    private String activeCode;

    private Date createTime;

    private Date updateTime;

    public Device(Integer id, Integer userId, Integer agentId, String deviceId, String deviceKey, String deviceType, String activeCode, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.agentId = agentId;
        this.deviceId = deviceId;
        this.deviceKey = deviceKey;
        this.deviceType = deviceType;
        this.activeCode = activeCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Device() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey == null ? null : deviceKey.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode == null ? null : activeCode.trim();
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
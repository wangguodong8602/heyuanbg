package com.background.vo;

import java.util.Date;

public class ShopperDevice {
    private Integer id;

    private Integer userId;

    private Integer agentId;

    private String shoppername;

    private String phone;

    private String zfbId;

    private String wxId;

    private String ysfId;

    private String authcode;

    private String address;

    private String bussinessLicense;

    private String deviceId;

    private String deviceKey;

    private String deviceType;

    private String activeCode;

    private Date createTime;

    private Date updateTime;

    public ShopperDevice(Integer id, Integer userId, Integer agentId, String shoppername, String phone, String zfbId, String wxId, String ysfId, String authcode, String address, String bussinessLicense, String deviceId, String deviceKey, String deviceType, String activeCode, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.agentId = agentId;
        this.shoppername = shoppername;
        this.phone = phone;
        this.zfbId = zfbId;
        this.wxId = wxId;
        this.ysfId = ysfId;
        this.authcode = authcode;
        this.address = address;
        this.bussinessLicense = bussinessLicense;
        this.deviceId = deviceId;
        this.deviceKey = deviceKey;
        this.deviceType = deviceType;
        this.activeCode = activeCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public ShopperDevice() {
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

    public String getShoppername() {
        return shoppername;
    }

    public void setShoppername(String shoppername) {
        this.shoppername = shoppername == null ? null : shoppername.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getZfbId() {
        return zfbId;
    }

    public void setZfbId(String zfbId) {
        this.zfbId = zfbId == null ? null : zfbId.trim();
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getYsfId() {
        return ysfId;
    }

    public void setYsfId(String ysfId) {
        this.ysfId = ysfId == null ? null : ysfId.trim();
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode == null ? null : authcode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBussinessLicense() {
        return bussinessLicense;
    }

    public void setBussinessLicense(String bussinessLicense) {
        this.bussinessLicense = bussinessLicense == null ? null : bussinessLicense.trim();
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
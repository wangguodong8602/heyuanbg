package com.background.vo;

public class EditShopper {
    private Integer id;

    private String shoppername;

    private String phone;

    private String zfbId;

    private String wxId;

    private String ysfId;

    private String authcode;


    public EditShopper() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
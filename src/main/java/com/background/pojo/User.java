package com.background.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private Integer parentId;

    private String realname;

    private String username;

    private String password;

    private String phone;

    private String identityId;

    private String bankId;

    private String question;

    private String answer;

    private Integer role;

    private Double rate;

    private Date createTime;

    private Date updateTime;

    public User(Integer id, Integer parentId, String realname, String username, String password, String phone, String identityId, String bankId, String question, String answer, Integer role,Double rate, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.realname = realname;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.identityId = identityId;
        this.bankId = bankId;
        this.question = question;
        this.answer = answer;
        this.role = role;
        this.rate = rate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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
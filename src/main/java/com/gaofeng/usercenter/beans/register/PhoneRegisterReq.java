package com.classs.usercenter.beans.register;

import com.didi.meta.javalib.JRequest;

import javax.validation.constraints.NotNull;

/**
 * @Author:gaofeng
 * @Date:2018/5/28
 * @Description:
 **/
public class PhoneRegisterReq extends JRequest {

    @NotNull
    private String username;
    @NotNull
    private String phone;
    @NotNull
    private String vertificationCode;
    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVertificationCode() {
        return vertificationCode;
    }

    public void setVertificationCode(String vertificationCode) {
        this.vertificationCode = vertificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

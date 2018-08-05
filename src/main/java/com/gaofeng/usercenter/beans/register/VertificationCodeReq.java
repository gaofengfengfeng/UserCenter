package com.classs.usercenter.beans.register;

import com.didi.meta.javalib.JRequest;

import javax.validation.constraints.NotNull;

/**
 * @Author:gaofeng
 * @Date:2018/5/29
 * @Description:
 **/
public class VertificationCodeReq extends JRequest {

    @NotNull
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

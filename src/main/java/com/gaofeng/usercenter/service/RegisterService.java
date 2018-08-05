package com.gaofeng.usercenter.service;

import com.gaofeng.dblib.model.User;
import com.gaofeng.dblib.model.UserMapper;
import com.classs.usercenter.beans.register.PhoneRegisterReq;
import com.didi.meta.javalib.IdUtil;
import com.didi.meta.javalib.JLog;
import org.springframework.stereotype.Service;

/**
 * @Author:gaofeng
 * @Date:2018/6/1
 * @Description: 处理用户注册业务逻辑的service
 **/
@Service
public class RegisterService {

    private UserMapper userMapper;

    public RegisterService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * @Author:gaofeng
     * @Date:2018/6/1
     * @Description: 用户通过手机号注册用户
     **/
    public boolean registerByPhone(PhoneRegisterReq phoneRegisterReq) {
        JLog.info("registerByPhone phone=" + phoneRegisterReq.getPhone());

        // 根据手机号和用户名查找数据库中是否已经有该用户，如果有直接返回false
        User userInDB = userMapper.selectUserByPhoneOrUsername(phoneRegisterReq.getPhone(), phoneRegisterReq
                .getUsername());
        if (userInDB != null) {
            JLog.error("already have user in db phone=" + phoneRegisterReq.getPhone() + " username=" +
                    phoneRegisterReq.getUsername(), 101022159);
            return false;
        }

        // 创建该用户对象
        User user = new User();
        user.setUserId(IdUtil.generateLongId());
        user.setUsername(phoneRegisterReq.getUsername());
        user.setPassword(phoneRegisterReq.getPassword());
        user.setPhone(phoneRegisterReq.getPhone());
        user.setStatus(User.Status.USED);
        // 将该用户插入数据库
        Integer affect;
        try {
            affect = userMapper.saveUser(user);
        } catch (Exception e) {
            JLog.error("save user exception username=" + phoneRegisterReq.getUsername() + " phone=" +
                    phoneRegisterReq.getPhone(), 101022150);
            e.printStackTrace();
            return false;
        }
        if (affect.equals(1)) {
            JLog.info("save user success phone=" + phoneRegisterReq.getPhone());
            return true;
        } else {
            JLog.error("save user failed phone=" + phoneRegisterReq.getPhone(), 101022152);
            return false;
        }
    }
}

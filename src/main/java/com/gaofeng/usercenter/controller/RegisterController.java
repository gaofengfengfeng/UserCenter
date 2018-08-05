package com.gaofeng.usercenter.controller;

import com.gaofeng.usercenter.InitConfig;
import com.gaofeng.usercenter.service.RegisterService;
import com.classs.usercenter.beans.register.PhoneRegisterReq;
import com.classs.usercenter.beans.register.VertificationCodeReq;
import com.didi.meta.javalib.JLog;
import com.didi.meta.javalib.JResponse;
import com.didi.meta.javalib.RandomNumUtil;
import com.didi.meta.javalib.service.JRedisPoolService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author:gaofeng
 * @Date:2018/5/28
 * @Description: 用户注册
 **/
@RestController
@RequestMapping(value = "register")
public class RegisterController {

    private RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * @Author:gaofeng
     * @Date:2018/5/28
     * @Description: 用户通过手机号发送验证码注册
     **/
    @RequestMapping(value = "phoneRegister")
    public JResponse phoneRegister(HttpServletRequest request, @RequestBody @Valid PhoneRegisterReq
            phoneRegisterReq) {
        JLog.info("phoneRegister phone=" + phoneRegisterReq.getPhone());

        JResponse jResponse = JResponse.initResponse(request, JResponse.class);

        // 根据手机号去redis内取验证码，如果取不到，返回验证码过期
        String rightVertificationCode;
        try {
            Jedis jedis = JRedisPoolService.getInstance(InitConfig.REDISPOOL);
            rightVertificationCode = jedis.get(phoneRegisterReq.getPhone());
        } catch (Exception e) {
            JLog.error("get value from redis failed phone=" + phoneRegisterReq.getPhone(), 101292242);
            jResponse.setErrNo(101292242);
            jResponse.setErrMsg("connect to redis failed");
            return jResponse;
        }
        if (phoneRegisterReq.getVertificationCode().equals(rightVertificationCode)) {
            JLog.info("vertificationCode is right vertificationCode=" + rightVertificationCode);
            // 创建用户
            boolean registerResult = registerService.registerByPhone(phoneRegisterReq);
            if (!registerResult) {
                JLog.error("register failed phone=" + phoneRegisterReq.getPhone(), 101020014);
                jResponse.setErrNo(101020014);
                jResponse.setErrMsg("register failed");
                return jResponse;
            }
        } else {
            JLog.info("vertificationCode is wrong requestCode=" + phoneRegisterReq.getVertificationCode() + " " +
                    "rightCode=" + rightVertificationCode);
            jResponse.setErrNo(101292248);
            jResponse.setErrMsg("vertificationCode is wrong");
            return jResponse;
        }
        return jResponse;
    }

    /**
     * @Author:gaofeng
     * @Date:2018/5/29
     * @Description: 获取验证码
     **/
    @RequestMapping(value = "getVertificationCode")
    public JResponse getVertificationCode(HttpServletRequest request, @RequestBody @Valid VertificationCodeReq
            vertificationCodeReq) {
        JLog.info("getVertificationCode phone=" + vertificationCodeReq.getPhone());

        JResponse jResponse = JResponse.initResponse(request, JResponse.class);

        //生成六位数验证码
        String vertificationCode = RandomNumUtil.geneSixRandomNum();
        //以用户手机号为key值，vertificationCode为value，存入redis中，并设置过期时间
        try {
            Jedis jedis = JRedisPoolService.getInstance(InitConfig.REDISPOOL);
            jedis.setex(vertificationCodeReq.getPhone(), InitConfig.RANDOM_NUM_EXPIRE_TIME, vertificationCode);
            JLog.info("set into redis success phone=" + vertificationCodeReq.getPhone() + " vertificationCode=" +
                    vertificationCode);
        } catch (Exception e) {
            JLog.error("set into redis failed phone=" + vertificationCodeReq.getPhone() + " vertificationCode=" +
                    vertificationCode, 101292234);
            jResponse.setErrNo(101292234);
            jResponse.setErrMsg("connect to redis failed");
            return jResponse;
        }

        jResponse.setData(vertificationCode);
        return jResponse;
    }


}

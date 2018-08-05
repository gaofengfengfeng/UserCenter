package com.gaofeng.usercenter;

import com.didi.meta.javalib.JResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:gaofeng
 * @Date:2018/5/24
 * @Description: 网络请求，请求controller方法经历的切面
 **/
@Aspect
@Configuration
public class WebLogicAspect {

    @Pointcut("execution(public * com.classs.usercenter.controller..*(..)) && @annotation(org.springframework.web" +
            ".bind.annotation.RequestMapping)")
    public void weblogic() {
    }

    @Around("weblogic()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }

    @AfterReturning(returning = "jResponse", pointcut = "weblogic()")
    public void doAfterReturning(JResponse jResponse) throws Throwable {
        jResponse.setServerTime(System.currentTimeMillis() / 1000);
    }


}

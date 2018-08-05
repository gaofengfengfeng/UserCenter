package com.gaofeng.usercenter;

import com.didi.meta.javalib.JProperties;
import com.didi.meta.javalib.service.JRedisPoolService;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;

/**
 * @Author: gaofeng
 * @Date: 2018-08-05
 * @Description:
 */
public class InitConfig {
    public static final String REDISPOOL = "redispool";
    public static Integer RANDOM_NUM_EXPIRE_TIME = 60;

    public static void init() {
        JProperties.init();
        // 注册单机redis服务
        JRedisPoolService.ConfObj redisPoolConfObj = new JRedisPoolService.ConfObj();
        redisPoolConfObj.confName = REDISPOOL;
        redisPoolConfObj.nodes = new HashSet<HostAndPort>();
        String redisPort = JProperties.getValueByKey("redis", "redis.port");
        String redisHost = JProperties.getValueByKey("redis", "redis.host");
        redisPoolConfObj.nodes.add(new HostAndPort(redisHost, Integer.parseInt(redisPort)));
        JRedisPoolService.registerConf(REDISPOOL, redisPoolConfObj);
    }
}

package com.four.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    private final Long EXPIRED_TIME = 120L;


    /**
     * 缓存验证码
     * @param pCode
     * @param code
     */
    public void saveCode(String pCode,String code)
    {
        String key = "code:"+pCode;
        stringRedisTemplate.opsForValue().set(key,code,120L,TimeUnit.SECONDS); // 有效期为120秒
    }

    /**
     * 根据pCode取出验证码
     * @param pCode
     * @return
     */
    public String getCode(String pCode)
    {
        String key = "code:"+pCode;
        String code = stringRedisTemplate.opsForValue().get(key);
        return code;
    }

    /**
     * 删除验证码缓存
     * @param pCode
     */
    public void deleteCode(String pCode)
    {
        String key = "code:"+pCode;
        stringRedisTemplate.delete(key);
    }

    /**
     * 缓存token
     * @param key
     * @param token
     */
    public void save(String key,String token)
    {
        key += "_token";
        stringRedisTemplate.opsForValue().set(key,token,EXPIRED_TIME, TimeUnit.MINUTES);
    }


    /**
     * 刷新token和身份信息的过期时间
     * @param key
     */
    public void refreshExpired(String key)
    {
        redisTemplate.expire(key,EXPIRED_TIME,TimeUnit.MINUTES);
        stringRedisTemplate.expire(key+"_token",EXPIRED_TIME,TimeUnit.MINUTES);
    }

    /**
     * 缓存用户身份
     * @param key
     * @param authentication
     */
    public void save(String key, Authentication authentication)
    {
        redisTemplate.opsForValue().set(key,authentication);
    }


    /**
     * 从缓存中获取token
     * @param key
     * @return
     */
    public String getToken(String key)
    {
        key += "_token";
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 从缓存中获取身份
     * @param key
     * @return
     */
    public Authentication getAuthentication(String key)
    {
        return (Authentication) redisTemplate.opsForValue().get(key);
    }


    /**
     * 清楚缓存
     * @param key
     */
    public void delete(String key)
    {
        redisTemplate.delete(key);
        stringRedisTemplate.delete(key+"_token");
    }

}

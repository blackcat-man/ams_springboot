package com.four.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
@Data
public class JwtUtils {

    static final String USER_NAME = "username";
    static final String TOKEN_SUB = "uuid";

    public static final long EXPIRE = 1000 * 60 * 60 * 24;//token过期时间   24小时
    public static final String APP_SECRET = "xUhUaN172.";//密钥


    /**
     * 生成token
     * @param username
     * @return
     */
    public String generateToken(String username){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("jacob-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim(TOKEN_SUB, UUID.randomUUID())
                .claim(USER_NAME, username)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseToken(String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }


    /**
     * 根据token，判断token是否存在与有效
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        if(StringUtils.isEmpty(token)) return false;
        try {
            Claims claims = parseToken(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 根据token获得用户名
     * @param token
     * @return
     */
    public String getUsername(String token)
    {
        Claims claims = parseToken(token);
        String username = (String) claims.get(USER_NAME);
        return username;
    }


    /**
     * 校验两个toekn是否一致
     * @param accessToken
     * @param validityToken
     * @return
     */
    public boolean validityToken(String accessToken,String validityToken)
    {
        if (validityToken != null)
        {
            if (accessToken.equals(validityToken))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据request判断token是否存在与有效（也就是把token取出来罢了）
     * @param request
     * @return
     */
    public boolean checkRequestToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("UserToken");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
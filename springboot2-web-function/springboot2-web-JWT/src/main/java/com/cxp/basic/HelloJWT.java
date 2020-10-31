package com.cxp.basic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-10-31 18:28
 */
public class HelloJWT {

    public static void main(String[] args) {
        String token = createToken();
        verifyToken(token);
    }

    /**
     * 创建JWT Token
     * @return
     */
    public static String createToken(){
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //当前时间向后2小时
        Date expireDate = Date.from(LocalDateTime.now().minusHours(-2).atZone(ZoneId.systemDefault()).toInstant());

        Algorithm algorithm = Algorithm.HMAC256("secret");

        String token = JWT.create()
                // 设置头部信息 Header
                .withHeader(map)
                //设置 载荷 签名是有谁生成 例如 服务器
                .withIssuer("SERVICE")
                //设置 载荷 签名的主题
                .withSubject("this is test token")
                // .withNotBefore(new Date())//设置 载荷 定义在什么时间之前，该jwt都是不可用的.
                //设置 载荷 签名的观众 也可以理解谁接受签名的
                .withAudience("APP")
                //设置 载荷 生成签名的时间
                .withIssuedAt(new Date())
                //设置 载荷 签名过期的时间
                .withExpiresAt(expireDate)
                //签名 Signature
                .sign(algorithm);

        return token;
    }

    /**
     * 验证JWT Token
     * @param token
     */
    public static void verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("SERVICE").build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String subject = decodedJWT.getSubject();
        String header = decodedJWT.getHeader();

        System.out.println(subject);
        System.out.println(header);
        System.out.println(new String(Base64.getDecoder().decode(header)));
    }
}

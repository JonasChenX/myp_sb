package com.jonas.myp_sb.example.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    //有效期為
    public static final Long JWT_TTL = 60 * 60 * 1000L; // 一個小時
    //設置密鑰明文
    public static final String JWT_KEY = "jonaschenxx";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    public static String createJWT(String subject){
       JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
       return builder.compact();
    }

    public static String createJWT(String subject, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID()); //設置過期時間
        return builder.compact();
    }

    public static String createJWT(String id, String subject, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id); //設置過期時間
        return builder.compact();
    }

    public static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        if(ttlMillis == null){
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = currentTimeMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid) //唯一的ID
                .setSubject(subject) //主機 可以是JSON數據
                .setIssuer("JC") //簽發者
                .setIssuedAt(now) //簽發時間
                .signWith(signatureAlgorithm, secretKey) // 使用HS256對稱加密算法簽名,第二參數為密鑰
                .setExpiration(expDate);

    }

    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }



}

package com.bczb.utils;

import com.bczb.pojo.User;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtils {

    private static long time = 1000*60*60*24;
    private static String Signature = "2dn8Q3YVh9Hz0Ds";
    public static String createToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        Map valueMap = new HashMap<String, Object>();
        valueMap.put("id", user.getId());
        valueMap.put("expiryDate", System.currentTimeMillis() + time);
        valueMap.put("power", user.getPower());
        String jwtToken = jwtBuilder
                // header
                .setHeaderParam("type", "JWT")
                .setHeaderParam("algo", "HS256")
                // payload
                .setClaims(valueMap)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS512, Signature)
                .compact();
        return jwtToken;
    }

    public static Claims getTokenInfo(String token){

        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(Signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        return claims;
    }


    public static boolean isTokenCorrect(String token){
        try {
            Map map = TokenUtils.getTokenInfo(token);
        }catch (Exception e){
            System.out.println("///////////  " + e.getMessage());
            return false;
        }
        return true;
    }
}


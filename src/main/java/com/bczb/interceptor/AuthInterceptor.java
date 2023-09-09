package com.bczb.interceptor;


import com.bczb.exceptions.AuthException;
import com.bczb.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        //从authorization中获取token
        String token = request.getHeader("Authorization");
//        token = "eyJ0eXBlIjoiSldUIiwiYWxnbyI6IkhTMjU2IiwiYWxnIjoiSFM1MTIifQ.eyJleHBpcnlEYXRlIjoxNjk0MzQwNzE2MjYxLCJzdWIiOiJ1c2VyIiwiaWQiOjQ1LCJwb3dlciI6MCwiZXhwIjoxNjk0MzQwNzE2LCJqdGkiOiI3NmNmNDFmMC0xMGMyLTQ2N2QtYjUyMi0wNGRkMGY5MWIxZjQifQ.YbnZGEeCeGeXCTXwloUk3LqSHNAQKJGMMKbqOLFuCuZeKBBV1BAspkmFCIeAhbQZUQU-_sli5FoGyhbgE3DYHQ";
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println("///////////  " + token);
        //验证token
        if(!TokenUtils.isTokenCorrect(token)){
            throw new AuthException("token错误");
        }
        //解析token
        Claims  claims =  TokenUtils.getTokenInfo(token);
        //将用户信息放入request中
        request.setAttribute("uId", claims.get("id"));
        return true;
    }

}

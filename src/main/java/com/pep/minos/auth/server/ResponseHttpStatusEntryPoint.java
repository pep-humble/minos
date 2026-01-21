package com.pep.minos.auth.server;

import com.alibaba.fastjson2.JSON;
import com.pep.base.mvc.customize.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 响应结果验证入口点
 *
 * @author liu.gang
 */
public class ResponseHttpStatusEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseResult responseResult = new ResponseResult()
                .setCode(HttpStatus.UNAUTHORIZED.value())
                .setMsg("身份验证过期，请重新登陆");
        JSON.writeTo(outputStream, responseResult);
    }
}

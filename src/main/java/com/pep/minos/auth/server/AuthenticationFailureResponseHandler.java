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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 认证失败 响应JSON给客户端
 *
 * @author liu.gang
 */
public class AuthenticationFailureResponseHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseResult responseResult = new ResponseResult()
                .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setMsg("用户名或密码错误");
        JSON.writeTo(outputStream, responseResult);
    }
}

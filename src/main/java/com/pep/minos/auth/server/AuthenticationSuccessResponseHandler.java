package com.pep.minos.auth.server;

import com.alibaba.fastjson2.JSON;
import com.pep.base.mvc.customize.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 认证成功 响应JSON给客户端
 *
 * @author liu.gang
 */
public class AuthenticationSuccessResponseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String id = session.getId();
        ResponseResult responseResult = new ResponseResult().setData(id);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        JSON.writeTo(outputStream, responseResult);
    }
}

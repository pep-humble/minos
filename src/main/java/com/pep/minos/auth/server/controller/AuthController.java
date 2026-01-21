package com.pep.minos.auth.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证-登录相关接口
 *
 * @author liu.gang
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证-登录")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "检查登录后设置项")
    @GetMapping("/check-login-setting")
    public List<String> checkLoginSetting() {
        return new ArrayList<>();
    }

    @Operation(summary = "获取当前登录者信息")
    @GetMapping("/info")
    public UserDetails info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }

}

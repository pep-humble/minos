package com.pep.minos.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统设置 用户管理
 *
 * @author liu.gang
 */
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
@Tag(name = "系统设置-用户管理")
public class UserController {
}

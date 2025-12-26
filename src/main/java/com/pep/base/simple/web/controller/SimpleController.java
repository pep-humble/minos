package com.pep.base.simple.controller;

import com.pep.base.form.CodeForm;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 简易控制器,调试专用
 *
 * @author liu.gang
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {

    /**
     * ping - pong
     *
     * @param codeForm ping请求
     * @return pong
     */
    @PostMapping("/ping")
    public String pong(@RequestBody(required = false) CodeForm codeForm) {
        return Optional
                .ofNullable(codeForm)
                .map(CodeForm::getCode)
                .filter(StringUtils::hasLength)
                .map(code -> String.join(":", "pong", code))
                .orElse("pong");
    }
}

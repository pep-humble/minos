package com.pep.minos.system.controller;

import com.pep.minos.system.form.setting.SettingCreateForm;
import com.pep.minos.system.form.setting.SettingModifyForm;
import com.pep.minos.system.service.SettingService;
import com.pep.minos.system.vo.setting.SettingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统设置接口
 *
 * @author liu.gang
 */
@RestController
@RequestMapping("/system/setting")
@RequiredArgsConstructor
@Tag(name = "系统设置-设置项管理")
public class SettingController {

    /**
     * 系统设置项服务
     */
    private final SettingService settingService;

    @PostMapping
    @Operation(summary = "创建系统设置项")
    public void createSetting(@RequestBody @Validated SettingCreateForm createForm) {
        settingService.createSetting(createForm);
    }

    @PutMapping
    @Operation(summary = "修改系统设置项")
    public void modifySetting(@RequestBody @Validated SettingModifyForm modifyForm) {
        settingService.modifySetting(modifyForm);
    }

    @GetMapping("/{key}")
    @Operation(summary = "根据唯一名称匹配系统设置项")
    @Parameter(name = "key", description = "设置项唯一名称", in = ParameterIn.PATH)
    public SettingVo first(@PathVariable String key) {
        return settingService.first(key);
    }
}

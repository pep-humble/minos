package com.pep.minos.system.service.impl;

import com.pep.minos.system.form.setting.SettingCreateForm;
import com.pep.minos.system.form.setting.SettingModifyForm;
import com.pep.minos.system.service.SettingService;
import com.pep.minos.system.vo.setting.SettingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统设置项服务
 * @author liu.gang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    @Override
    public void createSetting(SettingCreateForm createForm) {

    }

    @Override
    public void modifySetting(SettingModifyForm modifyForm) {

    }

    @Override
    public SettingVo first(String key) {
        return null;
    }
}

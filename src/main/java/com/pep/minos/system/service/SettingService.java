package com.pep.minos.system.service;

import com.pep.minos.system.form.setting.SettingCreateForm;
import com.pep.minos.system.form.setting.SettingModifyForm;
import com.pep.minos.system.vo.setting.SettingVo;

/**
 * 系统设置项服务
 *
 * @author liu.gang
 */
public interface SettingService {

    /**
     * 创建系统设置项
     *
     * @param createForm 创建表单
     */
    void createSetting(SettingCreateForm createForm);

    /**
     * 修改系统设置项
     *
     * @param modifyForm 修改表单
     */
    void modifySetting(SettingModifyForm modifyForm);

    /**
     * 根据唯一名称匹配设置项
     *
     * @param key 唯一名称
     * @return 设置项
     */
    SettingVo first(String key);
}

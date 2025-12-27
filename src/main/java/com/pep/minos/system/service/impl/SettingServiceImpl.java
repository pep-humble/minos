package com.pep.minos.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pep.base.exception.BusinessException;
import com.pep.base.util.StringUtil;
import com.pep.minos.system.entity.SystemSetting;
import com.pep.minos.system.form.setting.SettingCreateForm;
import com.pep.minos.system.form.setting.SettingModifyForm;
import com.pep.minos.system.mapper.SystemSettingMapper;
import com.pep.minos.system.service.SettingService;
import com.pep.minos.system.vo.setting.SettingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 系统设置项服务
 *
 * @author liu.gang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    /**
     * 系统设置项数据库操作器
     */
    private final SystemSettingMapper systemSettingMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSetting(SettingCreateForm createForm) {
        ensureUniqueness(createForm, null);
        SystemSetting systemSetting = convertSystemSetting(createForm);
        systemSetting.setUuid(UUID.randomUUID().toString());
        systemSettingMapper.insert(systemSetting);
    }

    /**
     * 确保唯一
     *
     * @param createForm 表单
     * @param id         唯一编号
     */
    private void ensureUniqueness(SettingCreateForm createForm, String id) {
        boolean exists = systemSettingMapper.exists(
                Wrappers.lambdaQuery(SystemSetting.class)
                        .eq(StringUtil.isFriendly(createForm.getKey()), SystemSetting::getKey, createForm.getKey())
                        .ne(StringUtil.isFriendly(id), SystemSetting::getUuid, id)
        );
        if (exists) {
            throw BusinessException.newException("设置项唯一名称已经存在", HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    /**
     * 确保存在
     *
     * @param id 唯一编号
     * @return 存在的数据
     */
    private SystemSetting ensureExistence(String id) {
        SystemSetting systemSetting = systemSettingMapper.selectOne(
                Wrappers.lambdaQuery(SystemSetting.class)
                        .eq(StringUtil.isFriendly(id), SystemSetting::getUuid, id)
        );
        if (Objects.isNull(systemSetting)) {
            throw BusinessException.newException("此编号不存在", HttpStatus.BAD_REQUEST.value(), null);
        }
        return systemSetting;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void modifySetting(SettingModifyForm modifyForm) {
        SystemSetting existSystemSetting = ensureExistence(modifyForm.getId());
        ensureUniqueness(modifyForm, modifyForm.getId());
        SystemSetting systemSetting = convertSystemSetting(modifyForm);
        systemSetting.setUuid(modifyForm.getId())
                .setId(existSystemSetting.getId());
        systemSettingMapper.updateById(systemSetting);
    }

    /**
     * 表单转换成数据库对象
     *
     * @param createForm 表单
     * @return 数据库对象
     */
    private SystemSetting convertSystemSetting(SettingCreateForm createForm) {
        return new SystemSetting()
                .setKey(createForm.getKey())
                .setDesc(createForm.getDesc())
                .setEnabled(createForm.isEnabled())
                .setProperties(createForm.getProperties());
    }

    @Override
    public SettingVo first(String key) {
        SystemSetting one = systemSettingMapper.selectOne(
                Wrappers.lambdaQuery(SystemSetting.class)
                        .eq(StringUtil.isFriendly(key), SystemSetting::getKey, key)
        );
        return Optional
                .ofNullable(one)
                .map(systemSetting -> new SettingVo()
                        .setId(systemSetting.getUuid())
                        .setCreateTime(systemSetting.getCreateTime())
                        .setCreator(systemSetting.getCreator())
                        .setModifyTime(systemSetting.getModifyTime())
                        .setModifier(systemSetting.getModifier())
                        .setKey(systemSetting.getKey())
                        .setDesc(systemSetting.getDesc())
                        .setEnabled(systemSetting.isEnabled())
                        .setProperties(systemSetting.getProperties()))
                .orElse(null);
    }
}

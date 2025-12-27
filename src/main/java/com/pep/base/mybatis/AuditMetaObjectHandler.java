package com.pep.base.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 元对象字段填充, 实现审计字段自动写入
 *
 * @author liu.gang
 */
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "creator", () -> "pep", String.class);
        this.strictInsertFill(metaObject, "modifier", () -> "pep", String.class);
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "modifyTime", Date::new, Date.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modifier", () -> "pep", String.class);
        this.strictUpdateFill(metaObject, "modifyTime", Date::new, Date.class);
    }
}

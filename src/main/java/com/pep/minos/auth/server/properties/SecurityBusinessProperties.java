package com.pep.minos.auth.server.properties;

import com.pep.base.path.PathMatcher;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 安全框架业务配置
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class SecurityBusinessProperties {

    /**
     * 免鉴权的路径匹配
     */
    private List<PathMatcher> permitAll;
}

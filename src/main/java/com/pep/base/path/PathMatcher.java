package com.pep.base.path;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

/**
 * 路径匹配
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class PathMatcher {

    /**
     * 匹配请求路径
     */
    private String path;

    /**
     * 匹配请求方法
     */
    private HttpMethod method;
}

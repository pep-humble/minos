package com.pep.minos.auth.server;

import com.pep.base.function.ListCollector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从请求头中获取sessionId 请求头名称: authorization
 *
 * @author liu.gang
 */
@Component
public class BearerHeaderHttpSessionIdResolver extends HeaderHttpSessionIdResolver {

    /**
     * 固定前缀
     */
    private final static String FIX_PREFIX = "bearer ";

    /**
     * The name of the header to obtain the session id from.
     */
    public BearerHeaderHttpSessionIdResolver() {
        super(HttpHeaders.AUTHORIZATION);
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        List<String> headers = super.resolveSessionIds(request);
        return headers.stream()
                .map(header -> {
                    if (Strings.CI.startsWith(header, FIX_PREFIX)) {
                        return header.substring(FIX_PREFIX.length());
                    }
                    return header;
                })
                .collect(new ListCollector<>(headers.size()));
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        // not do setSessionId
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        // not do expireSession
    }
}

package com.pep.minos.auth.server.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 用户信息查询服务
 *
 * @author liu.gang
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, "$2a$10$ZWilbfw9gJo6SNYsVu5F9.swN1Wxbgn2n4NOLVK/8H.O64yQkiPCu", new ArrayList<>());
    }
}

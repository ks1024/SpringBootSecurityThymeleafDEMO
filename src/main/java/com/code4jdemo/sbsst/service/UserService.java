package com.code4jdemo.sbsst.service;

import com.code4jdemo.sbsst.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void addUser(User user);
}

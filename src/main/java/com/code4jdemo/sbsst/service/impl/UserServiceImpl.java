package com.code4jdemo.sbsst.service.impl;

import com.code4jdemo.sbsst.domain.User;
import com.code4jdemo.sbsst.repository.UserRepository;
import com.code4jdemo.sbsst.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername {}", username);
        if (StringUtils.isEmpty(username)) {
            logger.error("username is empty");
            return null;
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("username not found");
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setAuthorities(grantedAuthorities);
        logger.info("loadUserByUsername find user {}", user);
        return user;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
}

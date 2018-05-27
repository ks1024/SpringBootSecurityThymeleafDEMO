package com.code4jdemo.sbsst.auth;

import com.code4jdemo.sbsst.domain.User;
import com.code4jdemo.sbsst.service.MessageService;
import com.code4jdemo.sbsst.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description 自定义AuthenticationProvider
 * @author K.Y
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    UserService userDetailsService;
    @Autowired
    MessageService messageService;//用于短信验证
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomUsernamePasswordAuthToken customUsernamePasswordAuthToken = (CustomUsernamePasswordAuthToken) authentication;
        String username = customUsernamePasswordAuthToken.getName();//username is telephone number
        String password = (String) customUsernamePasswordAuthToken.getCredentials();
        String code = (String) customUsernamePasswordAuthToken.getCode();
        String sessionUsername = (String) customUsernamePasswordAuthToken.getSessionUsername();
        String sessionCode = (String) customUsernamePasswordAuthToken.getSessionCode();
        Long sessionTime = (Long) customUsernamePasswordAuthToken.getSessionTime();
        UserDetails userDetails;
        logger.info("username:{} code:{} password:{} sessionUsername:{} sessionCode:{} sessionGmt:{}",
                username, code, password, sessionUsername, sessionCode, sessionTime);
        //如果验证码不为null，优先考虑验证码登录
        if (code != null && !messageService.isCodeValid(username, code, sessionUsername, sessionCode, sessionTime)) {
            return null;
        }
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
            return new CustomUsernamePasswordAuthToken(userDetails, password, code, sessionUsername, sessionCode, sessionTime, userDetails.getAuthorities());
        } catch (UsernameNotFoundException notFound) {
            //如果用户不存在，则创建新用户
            userDetailsService.addUser(new User(username, System.currentTimeMillis()));
            userDetails = userDetailsService.loadUserByUsername(username);
            return new CustomUsernamePasswordAuthToken(userDetails, password, code, sessionUsername, sessionCode, sessionTime, userDetails.getAuthorities());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (CustomUsernamePasswordAuthToken.class.isAssignableFrom(authentication));
    }
}

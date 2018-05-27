package com.code4jdemo.sbsst.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description 自定义 authentication token
 * @author K.Y
 */
public class CustomUsernamePasswordAuthToken extends UsernamePasswordAuthenticationToken {

    private Object code;            //verification code
    private Object sessionUsername; //username stored in session
    private Object sessionCode;     //code stored in session
    private Object sessionTime;     //time of sending code stored in session

    public CustomUsernamePasswordAuthToken(Object principal, Object credentials, Object code,
                                           Object sessionUsername, Object sessionCode, Object sessionTime) {
        super(principal, credentials);
        this.code = code;
        this.sessionUsername = sessionUsername;
        this.sessionCode = sessionCode;
        this.sessionTime = sessionTime;
    }

    public CustomUsernamePasswordAuthToken(Object principal, Object credentials, Object code,
                                           Object sessionUsername, Object sessionCode, Object sessionTime,
                                           Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.code = code;
        this.sessionUsername = sessionUsername;
        this.sessionCode = sessionCode;
        this.sessionTime = sessionTime;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getSessionUsername() {
        return sessionUsername;
    }

    public void setSessionUsername(Object sessionUsername) {
        this.sessionUsername = sessionUsername;
    }

    public Object getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(Object sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Object getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Object sessionTime) {
        this.sessionTime = sessionTime;
    }

}

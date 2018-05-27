package com.code4jdemo.sbsst.service;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {

    String sendVerificationCode(HttpServletRequest request, String phoneNumber);
    boolean isCodeValid(String phoneNumber, String code, String sessionPhoneNumber, String sessionCode, Long sessionGmt);
}

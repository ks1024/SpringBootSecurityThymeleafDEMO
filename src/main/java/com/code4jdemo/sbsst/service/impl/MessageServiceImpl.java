package com.code4jdemo.sbsst.service.impl;

import com.code4jdemo.sbsst.service.MessageService;
import com.code4jdemo.sbsst.utils.DateUtil;
import com.code4jdemo.sbsst.utils.RandomSecurityCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String KEY_SESSION_USERNAME = "s_username";
    private static final String KEY_SESSION_CODE = "s_code";
    private static final String KEY_SESSION_SEND_CODE_GMT = "s_send_code_gmt";

    protected final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Override
    public String sendVerificationCode(HttpServletRequest request, String phoneNumber) {

        HttpSession session = request.getSession();
        String verficationCode = "";
        if (session != null) {
            if (session.getAttribute(KEY_SESSION_USERNAME) != null
                    && phoneNumber.equals(session.getAttribute(KEY_SESSION_USERNAME))
                    && session.getAttribute(KEY_SESSION_SEND_CODE_GMT) != null
                    && DateUtil.getTime() - (Long) session.getAttribute(KEY_SESSION_SEND_CODE_GMT) < 300L) {
                //同一手机号且验证码为过期（5分钟）
                verficationCode = (String) session.getAttribute(KEY_SESSION_CODE);
            } else {
                //不同手机号或验证码过期 =》 重新生成验证码
                char[] charCode = RandomSecurityCodeUtil.getSecurityCode(4, RandomSecurityCodeUtil.SecurityCodeLevel.Simple, false);
                verficationCode = String.valueOf(charCode);
                //新验证码存入用户session，设置时效5分钟
                session.setAttribute(KEY_SESSION_USERNAME, phoneNumber);
                session.setAttribute(KEY_SESSION_CODE, verficationCode);
                session.setAttribute(KEY_SESSION_SEND_CODE_GMT, DateUtil.getTime());
            }
        }
        logger.info("send verification code {}", verficationCode);
        return verficationCode;
    }

    @Override
    public boolean isCodeValid(String phoneNumber, String code, String sessionPhoneNumber, String sessionCode, Long sessionGmt) {
        if (phoneNumber.equals(sessionPhoneNumber) && code.equals(sessionCode) && DateUtil.getTime() - sessionGmt < 300L) {
            return true;
        }
        return false;
    }
}

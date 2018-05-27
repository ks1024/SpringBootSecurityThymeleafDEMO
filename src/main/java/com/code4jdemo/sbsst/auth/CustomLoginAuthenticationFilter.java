package com.code4jdemo.sbsst.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Description 自定义login filter
 * @author K.Y
 */
public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    //~ Static fields/initializers ======================================================================
    public static final String FORM_USERNAME_KEY = "username";
    public static final String FORM_CODE_KEY = "code";
    public static final String FORM_PASSWORD_KEY = "password";
    private static final String SESSION_USERNAME_KEY = "s_username";
    private static final String SESSION_CODE_KEY = "s_code";
    private static final String SESSION_SEND_CODE_GMT_KEY = "s_send_code_gmt";
    private static final String DEFAULT_FAILURE_URL = "/signup?error";
    private static final String DEFAULT_LOGIN_PROCESSING_URL = "/signup";

    // default parameter values
    private String usernameParameter = FORM_USERNAME_KEY;
    private String codeParameter = FORM_CODE_KEY;
    private String passwordParameter = FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    private String loginProcessingUrl;

    //~ Constructors ====================================================================================
    public CustomLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(DEFAULT_LOGIN_PROCESSING_URL, "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(DEFAULT_FAILURE_URL));
    }

    public CustomLoginAuthenticationFilter(String loginProcessingUrl, AuthenticationManager authenticationManager,
                                           AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
//        super(loginProcessingUrl);
        super(new AntPathRequestMatcher(loginProcessingUrl, "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    //~ Methods =========================================================================================
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, UnsupportedEncodingException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String code = obtainCode(request);
        String password = obtainPassword(request);
        String sessionUsername = (String) obtainFromSession(request, SESSION_USERNAME_KEY);
        String sessionCode = (String) obtainFromSession(request, SESSION_CODE_KEY);
        Long sessionGmt = (Long) obtainFromSession(request, SESSION_SEND_CODE_GMT_KEY);

        if (username == null) {
            username = "";
        }

        username = username.trim();

        CustomUsernamePasswordAuthToken authRequest = new CustomUsernamePasswordAuthToken(username, password, code, sessionUsername, sessionCode, sessionGmt);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected Object obtainFromSession(HttpServletRequest request, String keyName) {
        if (request.getSession() != null && request.getSession().getAttribute(keyName) != null) {
            return request.getSession().getAttribute(keyName);
        }
        return null;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login request.
     *
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login request..
     *
     * @param passwordParameter the parameter name. Defaults to "password".
     */
    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the code from the login request..
     *
     * @param codeParameter the parameter name. Defaults to "code".
     */
    public void setCodeParameter(String codeParameter) {
        this.codeParameter = codeParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, an exception will
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getCodeParameter() {
        return codeParameter;
    }

    public boolean isPostOnly() {
        return postOnly;
    }
}

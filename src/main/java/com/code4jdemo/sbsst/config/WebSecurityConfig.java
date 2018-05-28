package com.code4jdemo.sbsst.config;

import com.code4jdemo.sbsst.auth.CustomAuthenticationProvider;
import com.code4jdemo.sbsst.auth.CustomLoginAuthenticationFilter;
import com.code4jdemo.sbsst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description 自定义 web security config
 * @author K.Y
 */
@Configuration
//@EnableWebSecurity
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    UserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //permit to access h2 console
                .antMatchers("/h2_console/**").permitAll()
                .antMatchers("/", "/index", "/about", "/send_code", "/signup/**", "/login/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //default login page
                .loginPage("/signup")
                //set login processing url for UsernamePasswordAuthenticationFilter
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index");
        //自定义的login authentication filter插入到UsernamePasswordAuthenticationFilter的位置
        http.authorizeRequests().and()
                .addFilterAt(new CustomLoginAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        //H2 database console runs inside a frame, So we need to disable X-Frame-Options in Spring Security.
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }



}

package com.code4jdemo.sbsst.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //~===========用户基本信息=============
    @Column(name = "username")
    private String username;    //用户手机号，为用户的唯一标识符
    @Column(name = "password")
    private String password;    //用户设置的密码
    @Column(name = "nickname")
    private String nickname;    //用户昵称
    @Column(name = "create_time")
    private Long createTime;    //创建时间
    @Transient
    private List<GrantedAuthority> authorities;

    public User() {
    }

    public User(String username, Long createTime) {
        this.username = username;
        //默认昵称为手机号隐去中间4位
        this.nickname = new StringBuilder("u").append(username.substring(0, 3))
                .append("****").append(username.substring(7)).toString();
        this.createTime = createTime;
    }

    public User(String username, String encodedPassword, Long createTime) {
        this(username, createTime);
        this.password = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime=" + createTime +
                ", authorities=" + authorities +
                '}';
    }
}

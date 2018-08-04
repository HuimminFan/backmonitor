package com.fhm.backmonitor.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fhm on 2018/7/19.
 */
@Entity
@ToString
@Table(name = "users")
public class User {

    private Long userId;
    private String userName;
    @JsonIgnore
    private String userPwd;

    private Long phone;
    private Integer role;   //角色id

    private String nickName; //昵称
    private String email;
    private String orgName; //部门名称
    private String remark;  //备注


    private String token;


    private String srpnames;


    @Transient
    public String getSrpnames() {
        return srpnames;
    }

    public void setSrpnames(String srpnames) {
        this.srpnames = srpnames;
    }

    @Transient
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Set<SRP> srps = new HashSet<SRP>();

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_name",nullable = false, length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_pwd",length = 50)
    @NotEmpty(message = "密码不能为空")
    @Size(max=50)
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Column(length = 20, unique = true)
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Column(name = "nick_name",length = 20)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "org_name",length = 20)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    @JsonBackReference
    @ManyToMany(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "user_srp",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "srp_id",referencedColumnName = "srpId")})
    public Set<SRP> getSrps() {
        return srps;
    }

    public void setSrps(Set<SRP> srps) {
        this.srps = srps;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", orgName='" + orgName + '\'' +
                ", remark='" + remark + '\'' +
                ", token='" + token + '\'' +

                '}';
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User() {
    }
}

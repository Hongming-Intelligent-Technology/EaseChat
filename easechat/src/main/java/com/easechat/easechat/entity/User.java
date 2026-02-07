package com.easechat.easechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easechat.easechat.enmus.Gender;
import com.easechat.easechat.enmus.Role;
import lombok.Data;

@Data
public abstract class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private String phone;

    private String email;

    private String address;

    private String icon;

    private String introduction;

    private Gender gender;

    private Role role;
}

package com.easechat.easechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easechat.easechat.enmus.Gender;
import lombok.Data;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private String phone;

    private String address;

    private String icon;

    private String introduction;

    private Gender gender;
}

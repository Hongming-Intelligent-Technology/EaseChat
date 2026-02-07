package com.easechat.easechat.enmus;

import lombok.Getter;

@Getter
public enum Role {
    NORMAL_USER("普通用户"),
    ADMIN_USER("管理员");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
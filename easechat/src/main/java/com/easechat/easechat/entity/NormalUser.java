package com.easechat.easechat.entity;

import com.easechat.easechat.enmus.*;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NormalUser extends User {

    public NormalUser() {
        this.setRole(Role.NORMAL_USER);
    }
}

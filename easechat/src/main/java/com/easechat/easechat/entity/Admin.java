package com.easechat.easechat.entity;

import com.easechat.easechat.enmus.Role;


import lombok.Data;
import lombok.ToString;

import lombok.EqualsAndHashCode;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    
    public Admin() {
        this.setRole(Role.ADMIN_USER);
    }

}

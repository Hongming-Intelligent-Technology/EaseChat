package com.easechat.easechat.entity;

import com.easechat.easechat.enmus.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    
    public Admin() {
        this.setRole(Role.ADMIN_USER);
    }
}

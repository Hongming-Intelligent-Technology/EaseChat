package com.easechat.easechat.entity;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NormalUser extends User {
    // Normal user role
}

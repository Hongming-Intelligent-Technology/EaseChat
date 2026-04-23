package com.easechat.easechat.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Gender {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final int code;

    @JsonValue
    private final String label;

    Gender(int code, String label) {
        this.code = code;
        this.label = label;
    }

    @JsonCreator
    public static Gender fromValue(Object value) {
        if (value == null) {
            return null;
        }

        String text = String.valueOf(value).trim();
        for (Gender gender : values()) {
            if (gender.name().equalsIgnoreCase(text)
                    || gender.label.equals(text)
                    || String.valueOf(gender.code).equals(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender: " + text);
    }
}

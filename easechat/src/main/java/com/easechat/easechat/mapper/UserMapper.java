package com.easechat.easechat.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easechat.easechat.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

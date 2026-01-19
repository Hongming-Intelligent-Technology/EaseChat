package com.easechat.easechat.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easechat.easechat.entity.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

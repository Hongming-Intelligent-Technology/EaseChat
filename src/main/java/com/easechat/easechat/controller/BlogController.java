package com.easechat.easechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easechat.easechat.mapper.BlogMapper;
import com.easechat.easechat.mapper.UserMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.utils.AuthUtil;
import com.easechat.easechat.entity.User;
import com.easechat.easechat.entity.Blog;
import com.easechat.easechat.entity.Comment;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogMapper blogMapper;

    @PostMapping("/save")
    public R save(@RequestBody Blog blog) {

        blog.setAccount(AuthUtil.getUserAccount());

        blog.setCreateTime(new Date());

        blogMapper.insert(blog);

        return R.success("博客添加成功");
    }

    @GetMapping("/list")
    public R list() {

        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Blog::getCreateTime);

        List<Blog> blogs = blogMapper.selectList(wrapper);

        return R.data(blogs);
    }
}

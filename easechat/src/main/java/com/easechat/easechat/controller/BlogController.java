package com.easechat.easechat.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easechat.easechat.mapper.BlogMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.utils.AuthUtil;
import com.easechat.easechat.entity.Blog;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

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
        return R.success("Blog saved successfully");
    }

    @GetMapping("/list")
    public R list() {
        List<Blog> blogs = new LambdaQueryChainWrapper<>(blogMapper)
                .orderByDesc(Blog::getCreateTime)
                .list();

        return R.data(blogs);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Blog blog) {
        Blog existingBlog = blogMapper.selectById(blog.getId());
        
        if (existingBlog == null) {
            return R.fail("Blog not found");
        }

        if (!existingBlog.getAccount().equals(AuthUtil.getUserAccount())) {
            return R.fail("Unauthorized to delete this blog");
        }

        blogMapper.deleteById(blog.getId());
        return R.success("Deleted successfully");
    }
}
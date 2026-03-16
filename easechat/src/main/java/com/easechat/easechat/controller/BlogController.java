package com.easechat.easechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easechat.easechat.mapper.BlogMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    public R<Void> save(@RequestBody Blog blog) {
        String currentAccount = AuthUtil.getUserAccount();
        
        // Validation
        if (blog.getTitle() == null || blog.getTitle().trim().isEmpty()) {
            return R.fail("Title cannot be empty");
        }
        
        blog.setAccount(currentAccount);
        
        // If author not provided, use account as author
        if (blog.getAuthor() == null || blog.getAuthor().trim().isEmpty()) {
            blog.setAuthor(currentAccount);
        }
        
        if (blog.getId() == null) {
            // Create new blog
            blog.setCreateTime(new Date());
            blogMapper.insert(blog);
        } else {
            // Update existing blog - check ownership first
            Blog existingBlog = blogMapper.selectById(blog.getId());
            if (existingBlog == null) {
                return R.fail("Blog not found");
            }
            if (!existingBlog.getAccount().equals(currentAccount)) {
                return R.fail("Unauthorized to update this blog");
            }
            // Keep original account and createTime
            blog.setAccount(existingBlog.getAccount());
            blog.setCreateTime(existingBlog.getCreateTime());
            blogMapper.updateById(blog);
        }
        
        return R.success("Blog saved successfully");
    }

    @GetMapping("/list")
    public R<List<Blog>> list() {
        List<Blog> blogs = new LambdaQueryChainWrapper<>(blogMapper)
                .orderByDesc(Blog::getCreateTime)
                .list();

        return R.data(blogs);
    }

    @GetMapping("/my")
    public R<List<Blog>> myBlogs() {
        String currentAccount = AuthUtil.getUserAccount();
        
        List<Blog> blogs = new LambdaQueryChainWrapper<>(blogMapper)
                .eq(Blog::getAccount, currentAccount)
                .orderByDesc(Blog::getCreateTime)
                .list();

        return R.data(blogs);
    }

    @GetMapping("/get")
    public R<Blog> getById(@RequestParam Integer id) {
        if (id == null) {
            return R.fail("Blog ID is required");
        }
        
        Blog blog = blogMapper.selectById(id);
        if (blog == null) {
            return R.fail("Blog not found");
        }
        
        return R.data(blog);
    }

    @PostMapping("/delete")
    public R<Void> delete(@RequestBody Blog blog) {
        if (blog.getId() == null) {
            return R.fail("Blog ID is required");
        }
        
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

    @PostMapping("/update")
    public R<Void> update(@RequestBody Blog blog) {
        if (blog.getId() == null) {
            return R.fail("Blog ID is required");
        }
        
        Blog existingBlog = blogMapper.selectById(blog.getId());
        
        if (existingBlog == null) {
            return R.fail("Blog not found");
        }

        if (!existingBlog.getAccount().equals(AuthUtil.getUserAccount())) {
            return R.fail("Unauthorized to update this blog");
        }

        // Validation
        if (blog.getTitle() == null || blog.getTitle().trim().isEmpty()) {
            return R.fail("Title cannot be empty");
        }

        // Keep original account and createTime
        blog.setAccount(existingBlog.getAccount());
        blog.setCreateTime(existingBlog.getCreateTime());
        
        // If author not provided, keep original
        if (blog.getAuthor() == null || blog.getAuthor().trim().isEmpty()) {
            blog.setAuthor(existingBlog.getAuthor());
        }

        blogMapper.updateById(blog);
        return R.success("Updated successfully");
    }
}
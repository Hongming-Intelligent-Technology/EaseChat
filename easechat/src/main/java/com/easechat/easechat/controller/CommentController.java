package com.easechat.easechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.utils.AuthUtil;
import com.easechat.easechat.entity.Comment;
import com.easechat.easechat.entity.User;
import com.easechat.easechat.mapper.CommentMapper;
import com.easechat.easechat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/save")
    public R<Void> save(@RequestBody Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            return R.fail("Message content cannot be empty");
        }
        
        if (comment.getRoomId() == null) {
            return R.fail("Room ID is required");
        }
        
        comment.setAccount(AuthUtil.getUserAccount());
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        return R.success("Message sent successfully");
    }

    @GetMapping("/list")
    public R<List<Comment>> list(@RequestParam Integer roomId) {
        if (roomId == null) {
            return R.fail("Room ID is required");
        }
        
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRoomId, roomId).orderByAsc(Comment::getCreateTime);

        List<Comment> comments = commentMapper.selectList(wrapper);

        Set<String> accounts = comments.stream()
                .map(Comment::getAccount)
                .filter(account -> account != null && !account.isBlank())
                .collect(Collectors.toSet());

        if (!accounts.isEmpty()) {
            LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.in(User::getAccount, accounts);
            Map<String, String> iconByAccount = userMapper.selectList(userWrapper).stream()
                    .collect(Collectors.toMap(User::getAccount, User::getIcon, (a, b) -> a));

            for (Comment item : comments) {
                item.setIcon(iconByAccount.get(item.getAccount()));
            }
        }

        return R.data(comments);
    }
}

package com.easechat.easechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.exception.ServiceException;
import com.easechat.easechat.common.utils.AuthUtil;
import com.easechat.easechat.entity.User;
import com.easechat.easechat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/loginOut")
    public R<Void> loginOut() {
        AuthUtil.clear();
        return R.success("Operation successful");
    }

    @GetMapping("/current")
    public R<User> current() {
        User user = AuthUtil.getUser();
        user.setPassword(null);
        return R.data(user);
    }

    @PostMapping("/register")
    public R<Void> register(@RequestBody User user) {
        if (user.getAccount() == null || user.getAccount().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ServiceException("Account and password are required");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, user.getAccount());
        Long count = userMapper.selectCount(wrapper);

        if (count > 0) {
            throw new ServiceException("Account already exists");
        }

        user.setPassword(AuthUtil.codePassword(user.getPassword()));
        userMapper.insert(user);
        return R.success("Registration successful");
    }

    @PostMapping("/login")
    public R<Void> login(@RequestBody User user) {
        if (user.getAccount() == null || user.getAccount().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {
            return R.fail("Username or password incorrect");
        }
        AuthUtil.clear();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, user.getAccount());
        User exist = userMapper.selectOne(wrapper);

        if (exist == null || !AuthUtil.matchesPassword(user.getPassword(), exist.getPassword())) {
            return R.fail("Username or password incorrect");
        }

        AuthUtil.setToken(exist);
        return R.success("Login successful");
    }

    @PostMapping("/update")
    public R<Void> update(@RequestBody User user) {
        User current = AuthUtil.getUser();
        User existing = userMapper.selectById(current.getId());
        if (existing == null) {
            throw new ServiceException("User not found");
        }

        existing.setPhone(user.getPhone());
        existing.setEmail(user.getEmail());
        existing.setAddress(user.getAddress());
        existing.setIcon(user.getIcon());
        existing.setGender(user.getGender());
        existing.setRemark(user.getRemark());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(AuthUtil.codePassword(user.getPassword()));
        }

        userMapper.updateById(existing);
        AuthUtil.setToken(existing);
        return R.success("Profile updated successfully");
    }

    @PostMapping("/delete")
    public R<Void> delete(@RequestBody User user) {
        User current = AuthUtil.getUser();
        if (user.getId() != null && !user.getId().equals(current.getId())) {
            throw new ServiceException("Unauthorized");
        }

        userMapper.deleteById(current.getId());
        AuthUtil.clear();
        return R.success("Operation successful");
    }
    
}

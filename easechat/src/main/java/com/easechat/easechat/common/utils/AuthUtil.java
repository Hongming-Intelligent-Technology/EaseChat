package com.easechat.easechat.common.utils;

import com.easechat.easechat.common.exception.ServiceException;
import com.easechat.easechat.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthUtil {
    private static final String TOKEN_KEY = "token";
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static Integer getUserId() {
        return getUser().getId();
    }

    public static String getUserAccount() {
        return getUser().getAccount();
    }

    public static void setToken(User auth) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TOKEN_KEY, auth);
    }

    public static void clear() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public static User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(TOKEN_KEY);

        if (attribute == null) {
            throw new ServiceException("User not logged in");
        }
        return (User) attribute;
    }

    public static String codePassword(String password) {
        if (password == null || password.isBlank()) {
            return password;
        }
        return PASSWORD_ENCODER.encode(password);
    }

    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }
}

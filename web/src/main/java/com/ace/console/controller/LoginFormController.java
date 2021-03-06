/**
 * Copyright (c) 2009-2018 https://github.com/denghp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.ace.console.controller;

import com.ace.console.bind.annotation.CurrentUser;
import com.ace.console.service.sys.UserService;
import com.ace.console.utils.Constants;
import com.ace.core.persistence.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Project_Name: ace
 * File: LoginUserController
 * User: denghp
 * Date: 10/16/13
 * Time: 6:09 PM
 */
@Controller
public class LoginFormController {
    private Logger logger = LoggerFactory.getLogger(LoginFormController.class);
    @Resource
    private UserService userService;

    @Value(value = "${shiro.login.url}")
    private String loginUrl;

    @Resource
    private MessageSource messageSource;

    @RequestMapping(value = {"/{login:login;?.*}"})
    public String loginForm(@CurrentUser User user, HttpServletRequest request, ModelMap model) {

        //表示退出
        if (!StringUtils.isEmpty(request.getParameter("logout"))) {
            model.addAttribute(Constants.MESSAGE, messageSource.getMessage("user.logout.success", null, null));
        }

        //表示用户删除了 @see org.apache.shiro.web.filter.user.SysUserFilter
        if (!StringUtils.isEmpty(request.getParameter("notfound"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.notfound", null, null));
        }

        //表示用户被管理员强制退出
        if (!StringUtils.isEmpty(request.getParameter("forcelogout"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.forcelogout", null, null));
        }

//        //表示用户输入的验证码错误
//        if (!StringUtils.isEmpty(request.getParameter("jcaptchaError"))) {
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("jcaptcha.validate.error", null, null));
//        }

        //登录失败了 提取错误消息
        Exception shiroLoginFailureEx =
                (Exception) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (shiroLoginFailureEx != null) {
            try {
                model.addAttribute(Constants.ERROR, messageSource.getMessage(shiroLoginFailureEx.getMessage(), null, null));
            } catch (Exception ex) {
                logger.error("user login error : ", ex);
                model.addAttribute(Constants.ERROR, messageSource.getMessage("user.unknown.error", null, null));
            }
        }

        //如果用户直接到登录页面 先退出一下
        //原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问
        // 这样会导致登录一直死循环
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }

        //如果同时存在错误消息 和 普通消息  只保留错误消息
        if (model.containsAttribute(Constants.ERROR)) {
            model.remove(Constants.MESSAGE);
        }

        return "/login";
    }


}

package com.ace.core.persistence.mapper;

import com.ace.core.persistence.entity.User;

/**
 * @Project_Name: ace
 * @File: UserMapper
 * (C) Copyright ACE Corporation 2014 All Rights Reserved.
 * @Author: denghp
 * @Date: 10/18/14
 * @Time: 4:01 PM
 * @Description:
 */
public interface UserMapper extends GenericeMapper<User, Long> {

    /**
     * 根据username查询USER
     * @param username
     * @return
     */
    public User findByUsername(String username);


}

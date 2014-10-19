package com.ace.core.persistence.mapper.impl;

import com.ace.core.persistence.entity.User;
import com.ace.core.persistence.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @Project_Name: ace
 * @File: UserMapperImpl
 * (C) Copyright ACE Corporation 2014 All Rights Reserved.
 * @Author: denghp
 * @Date: 10/18/14
 * @Time: 3:56 PM
 * @Description:
 */
@Repository
public class UserMapperImpl extends GenericeMapperImpl<User, Long> implements UserMapper {
    private Logger logger = LoggerFactory.getLogger(UserMapperImpl.class);
    public enum RdbOperation {

        FIND_BY_USERNAME(".findByUsername");

        private String value;

        private RdbOperation(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    @Override
    public User findByUsername(String username) {
        return getSqlSession().selectOne(getNamespace() + RdbOperation.FIND_BY_USERNAME.value(), username);
    }

}

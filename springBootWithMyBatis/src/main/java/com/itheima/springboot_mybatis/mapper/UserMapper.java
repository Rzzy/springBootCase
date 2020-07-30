package com.itheima.springboot_mybatis.mapper;

import com.itheima.springboot_mybatis.domain.User;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public List<User> queryUserList();
}

package com.n.dao;

import com.n.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {

    // 查询所有用户信息
    @Select("select * from cshelper_user")
    public List<User> findAll() throws Exception;

    // 根据username查询用户
    @Select("select * from cshelper_user where username = #{usernamr}")
    public User findByUsername(String username) throws Exception;
}

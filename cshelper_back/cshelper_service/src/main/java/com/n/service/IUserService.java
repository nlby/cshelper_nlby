package com.n.service;

import com.n.domain.User;

import java.util.List;

public interface IUserService {

    // 查询所有用户信息
    public List<User> findAll() throws Exception;

    // 用户登录检测
    public User checkLogin(String username, String password) throws Exception;
}

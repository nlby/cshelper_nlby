package com.n.service.impl;

import com.n.dao.IUserDao;
import com.n.domain.User;
import com.n.service.IUserService;
import com.n.utils.MDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public User checkLogin(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        if (user == null){
            user = new User();
            user.setFail("用户不存在");
            return user;
        }else{
            try {
                if(user.getPassword().equals(MDUtils.encodeMD2ToStr(password))){
                    user.setSuccess("登录成功");
                    return user;
                }else{
                    user.setFail("密码错误");
                    return user;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        user.setFail("未知错误");
        return user;
    }
}

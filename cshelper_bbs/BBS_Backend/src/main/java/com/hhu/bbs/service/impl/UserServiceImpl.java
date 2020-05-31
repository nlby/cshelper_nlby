package com.hhu.bbs.service.impl;

import com.hhu.bbs.entity.info.UserInfo;
import com.hhu.bbs.service.UserService;
import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.ExpDao;
import com.hhu.bbs.dao.FavoriteDao;
import com.hhu.bbs.dao.UserDao;
import com.hhu.bbs.entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * 用户 Service
 * @name  UserServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private FavoriteDao favoriteDao;
    private ExpDao expDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, FavoriteDao favoriteDao, ExpDao expDao) {
        this.userDao = userDao;
        this.favoriteDao = favoriteDao;
        this.expDao = expDao;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public FormatResult<UserInfo> login(String username, String password) {
        UserInfo userInfo = userDao.getUserInfoByUsernameAndPassword(username, password);
        if (userInfo == null){
            return FormatResultGenerator.genErrorResult("用户ID不存在");
        } else if (userInfo.getStatus() == 0){
            return FormatResultGenerator.genErrorResult("该用户已被拉黑");
        }
        updateLastLoginTime(username);
        expDao.addExpByUsername(username, 1);
        userInfo = userDao.getUserInfoByUsernameAndPassword(username, password);
        return FormatResultGenerator.genSuccessResult(userInfo);
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @Override
    public FormatResult<UserInfo> getUserInfoById(BigInteger id) {
        UserInfo userInfo = userDao.getUserInfoById(id);
        if (userInfo == null){
            return FormatResultGenerator.genErrorResult("用户ID不存在");
        } else if (userInfo.getStatus() == 0){
            return FormatResultGenerator.genErrorResult("该用户已被拉黑");
        }
        return FormatResultGenerator.genSuccessResult(userInfo);
    }

    /**
     * 注册新用户
     * @param nickname
     * @param username
     * @param password
     * @return
     */
    @Override
    public FormatResult<UserInfo> register(String nickname, String username, String password) {
        if (userDao.isNicknameExist(nickname) != null)
            return FormatResultGenerator.genErrorResult("昵称已经存在");
        if (userDao.isUsernameExist(username) != null)
            return FormatResultGenerator.genErrorResult("邮箱已被注册");
        int result = userDao.addUser(username, password, nickname);
        if (result == 0)
            return FormatResultGenerator.genErrorResult("无法插入数据库");
        UserInfo userInfo = userDao.getUserInfoByUsernameAndPassword(username, password);
        Favorite favorite = new Favorite();
        favorite.setName("默认收藏");
        favorite.setUserId(userInfo.getId());
        favoriteDao.addFavorite(favorite);
        return  FormatResultGenerator.genSuccessResult(userInfo);
    }

    /**
     * 更新用户信息
     * @param id
     * @param avatar
     * @param gender
     * @param workplace
     * @param description
     * @return
     */
    @Override
    public FormatResult<UserInfo> putUserInfo(BigInteger id, String avatar, String gender, String workplace, String description) {
        int i = userDao.putUserInfo(id, avatar, gender, workplace, description);
        if (i == 0){
            return FormatResultGenerator.genErrorResult("update userinfo error");
        }
        UserInfo userInfo = userDao.getUserInfoById(id);
        return FormatResultGenerator.genSuccessResult(userInfo);
    }

    /**
     * 更新用户的最后登录时间
     * @param username
     * @return
     */
    @Override
    public FormatResult<Object> updateLastLoginTime(String username) {
        return FormatResultGenerator.genSuccessResult(userDao.updateLastLoginTime(username));
    }

    /**
     * 设置用户token
     */
    @Override
    public FormatResult<String> checkUserExist(String nickname) {
        String userInfo = userDao.isNicknameExist(nickname);
        if (userInfo == null){
            return FormatResultGenerator.genErrorResult("用户不存在");
        }
        return FormatResultGenerator.genSuccessResult(userInfo);
    }

    @Override
    public int setToken(String token, BigInteger id) {
        return userDao.setToken(token, id);
    }
}

package com.hhu.bbs.service.impl;

import com.hhu.bbs.dao.*;
import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.entity.Admin;
import com.hhu.bbs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  管理员 Service
 * @name  AdminServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class AdminServiceImpl implements AdminService {
    private UserDao userDao;
    private AdminDao adminDao;
    private PostDao postDao;
    private BlockDao blockDao;
    private CommentDao commentDao;
    private ReplyDao replyDao;


    @Autowired
    public AdminServiceImpl(UserDao userDao, AdminDao adminDao, PostDao postDao, BlockDao blockDao, CommentDao commentDao, ReplyDao replyDao ) {
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.postDao = postDao;
        this.blockDao = blockDao;
        this.commentDao = commentDao;
        this.replyDao = replyDao;
    }

    /**
     * 加载主页信息
     * @return
     */
    @Override
    public FormatResult<Map<String, Object>> getIndexStatus() {
        return FormatResultGenerator.genSuccessResult(adminDao.getBasicInfoForBackend());
    }

    /**
     * 获得近七天每天发帖数目
     * @return
     */
    @Override
    public FormatResult<Map<String, Object>> getLastWeekStatus() {
        Map<String, Object> map = adminDao.getLastWeekStatus();
        return FormatResultGenerator.genSuccessResult(map);
    }

    /**
     * 查询所有用户列表
     * @param page
     * @param size
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAllUser(int page, int size) {
        int start = (page - 1) * size;
        List<Map<String, Object>> users = userDao.findAll(start, size);
        return FormatResultGenerator.genSuccessResult(users);
    }

    /**
     * 查询所有Post
     * @param page
     * @param size
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAllPost(int page, int size) {
        int start = (page -1) * size;
        List<Map<String, Object>> posts = postDao.findAll(start, size);
        return FormatResultGenerator.genSuccessResult(posts);
    }

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public FormatResult<Admin> login(String username, String password) {
        Admin admin = adminDao.getUserInfoByUsernameAndPassword(username, password);
        if (admin == null){
            return FormatResultGenerator.genErrorResult("用户名或密码错误");
        }
        return FormatResultGenerator.genSuccessResult(admin);
    }

    /**
     * 使用昵称模糊查询用户列表
     * @param pattern
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAllUserByNickname(String pattern) {
        String p = "%" + pattern + "%";
        List<Map<String, Object>> users = userDao.findAllByNickname(p);
        return FormatResultGenerator.genSuccessResult(users);
    }

    /**
     * 修改用户状态
     * @param id
     * @return
     */
    @Override
    public FormatResult<Object> changeUserStatus(BigInteger id, int status) {
        int result = userDao.changeStatusById(id, status);
        if (result <= 0){
            return FormatResultGenerator.genErrorResult("用户ID不存在");
        }

        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 修改与用户相关的数据的状态
     * @param id
     * @return
     */
    @Override
    public void changeAllStatusOfUser(BigInteger id, int status){
        postDao.changeAllPostStatusByUserId(id, status);
        commentDao.changeCommentStatusByUserId(id, status);
        replyDao.changeStatusByUserId(id, status);
    }

    /**
     * 添加版块
     * @param name
     * @param icon
     * @param description
     * @return
     */
    @Override
    @Transactional
    public FormatResult<Object> addBlock(String name, String icon, String description, BigInteger id) {
        int i  =  blockDao.addBlock(name, icon, description, id);
        if (i == 0){
            return FormatResultGenerator.genErrorResult("无法创建block");
        }
        userDao.changeStatusById(id, 2);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 修改block
     * @param id
     * @param name
     * @param description
     * @param adminId
     * @return
     */
    @Override
    public FormatResult<Object> putBlock(BigInteger id, String name, String icon, String description, BigInteger adminId) {
        int i = blockDao.putBlock(id, name, icon, description, adminId);
        if (i == 0){
            return FormatResultGenerator.genErrorResult("block ID 不存在");
        }
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 删除Block Status 置0
     * @param id
     * @return
     */
    @Override
    public FormatResult<Object> delBlock(BigInteger id) {
        int i = blockDao.changeStatusById(id,0);
        if (i == 0)
            return FormatResultGenerator.genErrorResult("无法删除ID: " + id);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 查询所有block信息
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAll() {
        List<Map<String, Object>> list = blockDao.findAll();
        return FormatResultGenerator.genSuccessResult(list);
    }

    /**
     * 得到用户页面数
     * @return
     */
    @Override
    public FormatResult<Integer> getUserPageNum(int size) {
        int num =  userDao.getUserSum();
        int sum = (num - 1) / size + 1;
        return FormatResultGenerator.genSuccessResult(sum);
    }

    /**
     * 得到Post页面页数
     * @return
     */
    @Override
    public FormatResult<Integer> getPostPageNum(int size) {
        int num =  postDao.getPostSum();
        int sum = (num - 1) / size + 1;
        return FormatResultGenerator.genSuccessResult(sum);
    }
}

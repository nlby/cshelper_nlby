package com.hhu.bbs.controller;

import com.hhu.bbs.service.UserService;
import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.entity.info.UserInfo;
import com.hhu.bbs.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 *  用户 Controller
 * @name  UserController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    private AuthenticationService authenticationService;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 对应用户信息
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public FormatResult<UserInfo> getUserInfo(@PathVariable("id")BigInteger id){
        return userService.getUserInfoById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public FormatResult<String> getUserInfo(@RequestParam("nickname")String nickname){
        return userService.checkUserExist(nickname);
    }
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public FormatResult<UserInfo> login(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password) {
        FormatResult<UserInfo> userInfoFormatResult = userService.login(username, password);
        if (userInfoFormatResult.isStatus()){
            String token = authenticationService.getToken(userInfoFormatResult.getData().getId(), password);
            userInfoFormatResult.getData().setToken(token);
            return userInfoFormatResult;
        }
        return userInfoFormatResult;
    }

    /**
     * 注册逻辑
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public FormatResult<UserInfo> register(@RequestParam(value = "nickname") String nickname, @RequestParam(value = "username") String username,
                                           @RequestParam(value = "password") String password) {
        FormatResult<UserInfo> userInfoFormatResult = userService.register(nickname, username, password);
        if (userInfoFormatResult.isStatus()){
            String token = authenticationService.getToken(userInfoFormatResult.getData().getId(), password);
            userInfoFormatResult.getData().setToken(token);
            UserInfo userInfo = userInfoFormatResult.getData();
            userService.setToken(token, userInfo.getId());  // 设置token
            return userInfoFormatResult;
        }
        return userInfoFormatResult;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public FormatResult<UserInfo> putUserInfo(@RequestParam("id")BigInteger id,
                                            @RequestParam("gender")String gender,
                                            @RequestParam("avatar") String avatar,
                                            @RequestParam("workPlace")String workplace,
                                            @RequestParam("description")String description){
        return userService.putUserInfo(id,  avatar, gender, workplace, description);
    }
}
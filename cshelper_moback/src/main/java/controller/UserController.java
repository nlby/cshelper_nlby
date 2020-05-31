package controller;

import domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public User login(@Param("username")String username, @Param("password")String password) throws Exception{

        return userService.checkLogin(new User(username,password));
    }

    /**
     * 添加用户，返回状态值
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/sign")
    @ResponseBody
    public User signup(@Param("username")String username,@Param("password")String password,@Param("phone")String phone,
                        @Param("mail")String mail) throws Exception{
        return userService.register(new User(username,password,phone,mail));
    }

}

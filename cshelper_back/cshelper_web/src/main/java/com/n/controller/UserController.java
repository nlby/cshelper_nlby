package com.n.controller;

import com.n.domain.User;
import com.n.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/findAll.do")
    public void findAll() throws Exception{
        ModelAndView mv = new ModelAndView();

        List<User> ps = userService.findAll();
//        mv.addObject("productList", ps);
//        mv.setViewName("product-list");
//        return mv;
        for (User u : ps) {
            System.out.println(u.getUsername());
        }
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, String username, String password) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:../pages/main.jsp");
        User user = userService.checkLogin(username, password);
        System.out.println(user.getMessage());
        mv.addObject("user", user);
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("userId", user.getId());
        if (user.getMessage().equals("登录成功")) {
        } else {
            mv.setViewName("login");
        }
        return mv;
    }
}

package com.n.controller;

import com.github.pagehelper.PageInfo;
import com.n.domain.Blog;
import com.n.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") int page, @RequestParam(name = "size", required = true, defaultValue = "10") int size, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        List<Blog> blogList = blogService.findAllByUserId(userId, page, size);

//        List<Blog> orderResult = new ArrayList<>();
//        for (int i = 1; i <= blogList.size(); i++) {
//            for (Blog blog : blogList) {
//                if (blog.getRownum() == i) {
//                    orderResult.add(blog);
//                }
//            }
//        }

        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(blogList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("blog-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(String title, String typef, String types, String content, String id, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        Blog blog = new Blog(title, typef, types, content, userId);
        if (id != null && id.length() > 0) { // 确保更新blog而不是删除
            blog.setId(id);
        }
        blogService.save(blog);
        return "redirect: findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
        Blog blog = blogService.findById(id);
        mv.addObject("blog", blog);
        mv.setViewName("editor");
        return mv;
    }
}

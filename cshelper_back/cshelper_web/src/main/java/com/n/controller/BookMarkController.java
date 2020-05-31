package com.n.controller;

import com.github.pagehelper.PageInfo;
import com.n.domain.BookMark;
import com.n.service.IBookMarkService;
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
@RequestMapping("/bookmark")
public class BookMarkController {
    @Autowired
    private IBookMarkService bookMarkService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") int page, @RequestParam(name = "size", required = true, defaultValue = "10") int size, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        List<BookMark> bookMarkList = bookMarkService.findAllByUserId(userId, page, size);

        List<BookMark> orderResult = new ArrayList<>();  // 此段是为记录按行号排序的错误写法，留此说明
        for (int i = 1; i <= bookMarkList.size(); i++) {
            for (BookMark bookMark : bookMarkList) {
                if (bookMark.getRownum() == i) {
                    orderResult.add(bookMark);
                }
            }
        }

        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(bookMarkList);
//        System.out.println(pageInfo.getPageSize() + "--------------------------");
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("bookmark-list");
        return mv;
    }
}

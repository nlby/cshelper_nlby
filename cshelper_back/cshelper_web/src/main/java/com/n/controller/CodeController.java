package com.n.controller;

import com.github.pagehelper.PageInfo;
import com.n.domain.Code;
import com.n.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private ICodeService codeService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "10") int page, @RequestParam(name = "size", required = true, defaultValue = "4") int size, @RequestParam(name = "userId", required = false, defaultValue = "") String userId, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Code> codeList = codeService.findAllByUserId(userId, page, size);
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(codeList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("code-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(String title, String typef, String types, String content, String id, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        Code code = new Code(title, typef, types, content, userId);
        if (id != null && id.length() > 0) { // 确保更新code而不是删除
            code.setId(id);
        }
        codeService.save(code);
        return "redirect: findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
        Code code = codeService.findById(id);
        mv.addObject("code", code);
        mv.setViewName("code-editor");
        return mv;
    }

    @RequestMapping("/findAll_web.do")
    public ModelAndView findAllWeb(@RequestParam(name = "page", required = true, defaultValue = "10") int page, @RequestParam(name = "size", required = true, defaultValue = "10") int size, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        List<Code> codeList = codeService.findAllByUserId(userId, page, size);

        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(codeList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("code-list-web");
        return mv;
    }

    @RequestMapping("/save_web.do")
    public String save_web(String title, String typef, String types, String content, String id, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        Code code = new Code(title, typef, types, content, userId);
        if (id != null && id.length() > 0) { // 确保更新code而不是删除
            code.setId(id);
        }
        codeService.save(code);
        return "redirect: findAll_web.do";
    }

    @RequestMapping("/findById_web.do")
    public ModelAndView findById_web(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
        Code code = codeService.findById(id);
        mv.addObject("code", code);
        mv.setViewName("code-editor-web");
        return mv;
    }
}

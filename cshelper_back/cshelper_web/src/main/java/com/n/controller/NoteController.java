package com.n.controller;

import com.github.pagehelper.PageInfo;
import com.n.domain.Blog;
import com.n.domain.BookMark;
import com.n.domain.Note;
import com.n.service.IBookMarkService;
import com.n.service.INoteService;
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
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private INoteService noteService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") int page, @RequestParam(name = "size", required = true, defaultValue = "10") int size, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        List<Note> noteList = noteService.findAllByUserId(userId, page, size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(noteList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("note-list");
        return mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
        Note note = noteService.findById(id);
        mv.addObject("note", note);
        mv.setViewName("note-show");
        return mv;
    }
}

package controller;

import domain.BaseBean;
import domain.BookMark;
import domain.Note;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.NoteService;
import utils.ConvertUtils;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    /**
     * 添加或更新笔记
     *
     * @param
     * @param rid
     * @param title
     * @param body
     * @param createAt
     * @param updateAt
     * @param userId
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public BaseBean addNote(@Param("rid") String rid, @Param("title") String title,
                                @Param("body") String body, @Param("createAt")String createAt, @Param("updateAt")String updateAt,
                                @Param("userId") String userId) throws Exception{
        Note note = new Note(rid, title, body, createAt, updateAt, userId);
        noteService.insertOrUpdate(note);
        if (note.getId() == null)
            return new BaseBean().fail();
        return new BaseBean().success();
    }
}

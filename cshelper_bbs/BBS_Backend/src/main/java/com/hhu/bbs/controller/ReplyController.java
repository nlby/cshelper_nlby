package com.hhu.bbs.controller;
import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 *  回复 Controller
 * @name  ReplyController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping(value = "/reply")
public class ReplyController {
    private ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    /**
     * 添加回复
     * @param userId 回复者id
     * @param commentId  回复的评论id
     * @param content 回复内容
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormatResult<Object> addReply(@RequestParam("userId") BigInteger userId,
                                         @RequestParam("commentId")BigInteger commentId,
                                         @RequestParam("content")String content){
        return replyService.addReply(userId, commentId, content);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public FormatResult<Object> delReply(@PathVariable(value = "id")BigInteger id){
        return replyService.delReply(id, 0);
    }
}

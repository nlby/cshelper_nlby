package com.hhu.bbs.controller;


import com.hhu.bbs.service.CommentService;
import com.hhu.bbs.util.format.FormatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  评论 Controller
 * @name  CommentController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    /**
     *
     * @param userId 用户id
     * @param postId 分类id
     * @param content 回复内容
     * @return
     */
    //    @TokenRequired
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormatResult<BigInteger> addComment(@RequestParam("userId") BigInteger userId, @RequestParam("postId") BigInteger postId, @RequestParam("content") String content){
        return commentService.addComment(userId,postId,content);
    }
    /**
     * 删除评论
     * @param id
     * @return
     */
//    @TokenRequired
    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public FormatResult<Integer> delComment(@PathVariable(value = "id")BigInteger id){
        return commentService.delComment(id);
    }

    @RequestMapping(value = "/user/id/{id}",  method = RequestMethod.GET)
    public FormatResult<List<Map<String, Object>>> getAllCommentByUserId(@PathVariable("id") BigInteger id){
        return commentService.getAllCommentByUserId(id);
    }



//    /**
//     * 查询某post下的评论
//     * @param id
//     * @param page 页数
//     * @return

//     */
//    @RequestMapping(value = "/post/{id}/page/{page}", method = RequestMethod.GET)
//    public FormatResult<List<Comment>> findAllCommentByPostId(@PathVariable(value = "id") BigInteger id, @PathVariable(value = "page") int page){
//        return null;
//    }
}

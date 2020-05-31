package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.CommentDao;
import com.hhu.bbs.dao.ExpDao;
import com.hhu.bbs.entity.Comment;
import com.hhu.bbs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  评论 Service
 * @name  CommentServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;
    private ExpDao expDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, ExpDao expDao){
        this.commentDao = commentDao;
        this.expDao = expDao;
    }

    /**
     * 添加评论
     * @return 插入主键
     */
    @Override
    public FormatResult<BigInteger> addComment(BigInteger userId, BigInteger postId, String content) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setContent(content);
        int id= commentDao.addComment(comment);
        expDao.addExpByUserId(userId, 3);
        return FormatResultGenerator.genSuccessResult(comment.getId());
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public FormatResult<Integer> delComment(BigInteger id) {
        commentDao.changeStatusById(id,0);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 根据UserId查找所有的评论
     * @param userId
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> getAllCommentByUserId(BigInteger userId) {
        return FormatResultGenerator.genSuccessResult(commentDao.getAllCommentByUserId(userId));
    }

//    @Override
//    public FormatResult<List<Comment>> findAllCommentByPostId(BigInteger id, int page) {
//        return null;
//    }
}

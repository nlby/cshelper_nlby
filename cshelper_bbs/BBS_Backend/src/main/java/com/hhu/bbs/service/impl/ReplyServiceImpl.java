package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.ExpDao;
import com.hhu.bbs.dao.ReplyDao;
import com.hhu.bbs.entity.Reply;
import com.hhu.bbs.service.ReplyService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 *  回复 Service
 * @name  ReplyServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    private ReplyDao replyDao;
    private ExpDao expDao;
    public ReplyServiceImpl(ReplyDao replyDao, ExpDao expDao){
        this.replyDao = replyDao;
        this.expDao = expDao;
    }

    /**
     * 添加2级回复
     * @param userId 回复者id
     * @param commentId  回复的评论id
     * @param content 回复内容
     * @return
     */
    @Override
    public FormatResult<Object> addReply(BigInteger userId, BigInteger commentId, String content) {
        Reply reply = new Reply();
        reply.setUserId(userId);
        reply.setCommentId(commentId);
        reply.setContent(content);
        replyDao.addReply(reply);
        expDao.addExpByUserId(userId,3);
        return FormatResultGenerator.genSuccessResult(reply.getId());
    }

    /**
     * 删除二级回复 （id)
     * @param id 回复
     * @param status 状态
     */
    @Override
    public FormatResult<Object> delReply(BigInteger id, int status) {
        int a = replyDao.changeStatuById(id,0);
        return FormatResultGenerator.genSuccessResult(a);
    }
}

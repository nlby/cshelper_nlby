package com.hhu.bbs.service;

import com.hhu.bbs.util.format.FormatResult;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 添加评论
     * @return 插入主键
     */
    FormatResult<BigInteger> addComment(BigInteger userId, BigInteger postId, String content);

    /**
     * 删除
     * @param id
     * @return
     */
    FormatResult<Integer> delComment(BigInteger id);

    /**
     * 根据UserId查找所有的评论
     * @param userId
     * @return
     */
    FormatResult<List<Map<String, Object>>> getAllCommentByUserId(BigInteger userId);
//    /**
//     * 分页查询某个post下的评论
//     * @param id
//     * @param page
//     * @return
//     */
//    FormatResult<List<Comment>> findAllCommentByPostId(BigInteger id, int page);

}

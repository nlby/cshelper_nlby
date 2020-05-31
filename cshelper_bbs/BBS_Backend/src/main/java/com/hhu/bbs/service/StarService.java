package com.hhu.bbs.service;

import com.hhu.bbs.util.format.FormatResult;

import java.math.BigInteger;
import java.util.Map;

public interface StarService {

    /**
     * 添加收藏
     * @param postId
     * @param favoriteId
     * @return
     */
    FormatResult<BigInteger> addStar(BigInteger postId, BigInteger favoriteId);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    FormatResult<BigInteger> delStar(BigInteger id);

    /**
     * 查询是否被收藏
     * @param userId
     * @param postId
     * @return
     */
    FormatResult<Map<String, Object>> isStar(BigInteger userId, BigInteger postId);

}

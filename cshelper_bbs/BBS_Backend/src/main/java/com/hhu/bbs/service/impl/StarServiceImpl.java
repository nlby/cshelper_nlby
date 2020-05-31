package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.StarDao;
import com.hhu.bbs.entity.Star;
import com.hhu.bbs.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

/**
 *  收藏 Service
 * @name  StarServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    /**
     * 添加收藏
     * @param postId
     * @param favoriteId
     * @return
     */
    @Override
    public FormatResult<BigInteger> addStar(BigInteger postId, BigInteger favoriteId) {
        Star star = new Star();
        star.setPostId(postId);
        star.setFavoriteId(favoriteId);
        starDao.addStar(star);
        return FormatResultGenerator.genSuccessResult(star.getId());
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @Override
    public FormatResult<BigInteger> delStar(BigInteger id) {
        starDao.delStar(id);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 查询是否被收藏
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public FormatResult<Map<String, Object>> isStar(BigInteger userId, BigInteger postId) {
        Map<String, Object> result = starDao.isStar(userId, postId);
        if (result == null){
            return FormatResultGenerator.genErrorResult("post is not favorite");
        }
        return FormatResultGenerator.genSuccessResult(result);
    }

}

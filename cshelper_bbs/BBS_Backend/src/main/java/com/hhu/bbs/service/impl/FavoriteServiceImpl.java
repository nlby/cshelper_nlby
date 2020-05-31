package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.FavoriteDao;
import com.hhu.bbs.entity.Favorite;
import com.hhu.bbs.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * 收藏夹 Service
 * @name  FavoriteServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    /**
     * 查询某用户的收藏夹
     * @param id
     * @return
     */
    @Override
    public FormatResult<List<Favorite>> findAllFavoriteByUserId(BigInteger id)
    {
        List<Favorite> list = favoriteDao.findFavoriteByUserId(id);
        return FormatResultGenerator.genSuccessResult(list);
    }

    /**
     * 删除favorite
     * @param id
     * @return 插入信息，成功则genSucess , 否则genError
     */
    @Override
    public FormatResult<Object> delFavorite(BigInteger id)
    {
        int a = favoriteDao.delFavoriteById(id);
        return FormatResultGenerator.genSuccessResult(a);
    }

    /**
     * 更新favorite
     * 能更新的属性只有名称
     * @param id 收藏夹id
     * @param name
     * @return 插入信息，成功则genSucess , 否则genError
     */
    @Override
    public FormatResult<Object> putFavorite(BigInteger id, String name) {
        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setName(name);
        int a = favoriteDao.putFavorite(favorite);
        return FormatResultGenerator.genSuccessResult(a);
    }

    /**
     * 添加favorite
     * @param userId 收藏夹主人
     * @param name  收藏夹名称
     * @return  成功返回主键
     */
    @Override
    public FormatResult<BigInteger> addFavorite(BigInteger userId, String name) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setName(name);
        int a = favoriteDao.addFavorite(favorite);
        return FormatResultGenerator.genSuccessResult(favorite.getId());
    }

    /**
     * 删除用户所有收藏夹
     * @param userId
     * @retur
     */
    @Override
    public FormatResult<Object> delFavoriteByUserId(BigInteger userId) {
        int a = favoriteDao.delFavoriteByUserId(userId);
        return FormatResultGenerator.genSuccessResult(a);
    }
}

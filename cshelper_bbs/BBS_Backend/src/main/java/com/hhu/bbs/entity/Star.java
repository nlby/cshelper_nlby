package com.hhu.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 *  收藏
 * @name  Star
 * @author  nlby
 * @date  2020/5/14
 */
public class Star {
    //收藏记录的ID
    private BigInteger id;
    //所对应的收藏夹ID
    private BigInteger favoriteId;
    //每条记录所对应的帖子ID
    private BigInteger postId;
    //收藏记录的创建时间
    private Timestamp createTime;
    //收藏记录的更新时间
    private Timestamp updateTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(BigInteger favoriteId) {
        this.favoriteId = favoriteId;
    }

    public BigInteger getPostId() {
        return postId;
    }

    public void setPostId(BigInteger postId) {
        this.postId = postId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Star{" +
                "id=" + id +
                ", favoriteId=" + favoriteId +
                ", postId=" + postId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
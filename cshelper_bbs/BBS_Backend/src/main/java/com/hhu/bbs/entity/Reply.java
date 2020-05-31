package com.hhu.bbs.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhu.bbs.entity.info.UserInfo;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 *  对帖子评论的回复 二级回复
 * @name  Reply
 * @author  nlby
 * @date  2020/5/14
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reply {
    //回复ID
    private BigInteger id;
    //该条回复的回复内容
    private String content;
    //回复所对应的评论id
    private BigInteger commentId;
    //该条回复的回复者id
    private BigInteger userId;
    //回复的创建时间

    private Timestamp createTime;
    //回复的更新时间
    private Timestamp updateTime;
    //该条回复的回复者
    private UserInfo userInfo;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public BigInteger getCommentId() {
        return commentId;
    }

    public void setCommentId(BigInteger commentId) {
        this.commentId = commentId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", userInfo=" + userInfo +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
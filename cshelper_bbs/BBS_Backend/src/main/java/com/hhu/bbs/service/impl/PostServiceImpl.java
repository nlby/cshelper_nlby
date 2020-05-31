package com.hhu.bbs.service.impl;

import com.hhu.bbs.entity.info.PostInfo;
import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.ExpDao;
import com.hhu.bbs.dao.PostDao;
import com.hhu.bbs.entity.Post;
import com.hhu.bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  帖子 Service
 * @name  PostServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class PostServiceImpl implements PostService {
    private PostDao postDao;
    private ExpDao expDao;

    @Autowired
    public PostServiceImpl(PostDao postDao, ExpDao expDao){
        this.postDao = postDao;
        this.expDao = expDao;
    }

    /**
     * 查询对应ID的post详细信息
     * @param id
     * @return
     */
    @Override
    public FormatResult<Post> getPostById(BigInteger id) {
        Post post = postDao.getPostById(id);
        if (post == null){
            return FormatResultGenerator.genErrorResult("post:"+ id +" not exist");
        }
        return FormatResultGenerator.genSuccessResult(post);
    }

    /**
     * 查询某个category下的所有post
     * @param id category id
     * @param startIndex 开始
     * @param pageSize 页面大小
     * @param sortType 排序方式
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findAllPostByCategoryId(BigInteger id, int startIndex, int pageSize, String sortType) {
       List<PostInfo> postInfos = postDao.findAllPostInfoByCategoryId(id, startIndex, pageSize, sortType);
       if (postInfos == null){
           return FormatResultGenerator.genErrorResult("category id not exist");
       }
       return FormatResultGenerator.genSuccessResult(postInfos);
    }

    /**
     * 查询某个block下的所有post
     * @param blockId 版块id
     * @param startIndex 开始
     * @param pageSize 页面大小
     * @param sortType 排序方式
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findAllPostByBlockId(BigInteger blockId, int startIndex, int pageSize, String sortType) {
        List<PostInfo> postInfos = postDao.findAllPostInfoByBlockId(blockId, startIndex, pageSize, sortType);
        if(postInfos == null){
            return FormatResultGenerator.genErrorResult("block is not exist");
        }
        return FormatResultGenerator.genSuccessResult(postInfos);
    }

    /**
     * 查询某个用户下的所有post
     * @param id category id
     * @param startIndex 开始
     * @param pageSize 页面大小
     * @param sortType 排序方式
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findAllPostByUserId(BigInteger id, int startIndex, int pageSize, String sortType) {
        List<PostInfo> postInfos = postDao.findAllPostInfoByUserId(id, startIndex, pageSize, sortType);
        if (postInfos == null){
            return FormatResultGenerator.genErrorResult("user id not exist");
        }
        return FormatResultGenerator.genSuccessResult(postInfos);
    }

    /**
     * 查询某个用户下的所有提问
     * @param id category id
     * @param startIndex 开始
     * @param pageSize 页面大小
     * @param sortType 排序方式
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findQuestionByUserId(BigInteger id, int startIndex, int pageSize, String sortType) {
        List<PostInfo> postInfos = postDao.findQuestionByUserId(id, startIndex, pageSize, sortType);
        if (postInfos == null){
            return FormatResultGenerator.genErrorResult("user id not exist");
        }
        return FormatResultGenerator.genSuccessResult(postInfos);
    }

    /**
     * 根据收藏夹ID查询
     * @param id
     * @param startIndex
     * @param pageSize
     * @param sortType
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findAllPostByFavoriteId(BigInteger id, int startIndex, int pageSize, String sortType) {
        List<PostInfo> postInfos = postDao.findAllPostInfoByFavoriteId(id, startIndex, pageSize, sortType);
        if (postInfos == null){
            return FormatResultGenerator.genErrorResult("favorite id not exist");
        }
        return FormatResultGenerator.genSuccessResult(postInfos);
    }

    /**
     * 关键字搜索帖子
     * @param keyword
     * @param startIndex
     * @param pageSize
     * @param sortType
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> findAllPostInfoByKeyword(String keyword, int startIndex, int pageSize, String sortType){
        return FormatResultGenerator.genSuccessResult(postDao.findAllPostInfoByKeyword(keyword, startIndex, pageSize, sortType));
    }

    /**
     * 添加Post
     * @param userId 用户id
     * @param categoryId 分类id
     * @param title 标题
     * @param content 内容
     * @return
     */
    @Override
    public FormatResult<BigInteger> addPost(BigInteger userId, BigInteger categoryId, String title, String content) {
        Post post = new Post();
        post.setUserId(userId); post.setCategoryId(categoryId);
        post.setTitle(title); post.setContent(content);
        postDao.addPost(post);
        expDao.addExpByUserId(userId, 5);
        return FormatResultGenerator.genSuccessResult(post.getId());
    }

    /**
     * 更新实体
     * @param userId 用户id
     * @param categoryId 分类id
     * @param title 标题
     * @param content 内容
     * @return
     */
    @Override
    public FormatResult<Object> putPost(BigInteger id,BigInteger userId, BigInteger categoryId, String title, String content) {
        Post post = new Post();
        post.setId(id);
        post.setUserId(userId); post.setCategoryId(categoryId);
        post.setTitle(title); post.setContent(content);
        postDao.putPost(post);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 删除Post
     * @param id
     * @return
     */
    @Override
    public FormatResult<Object> changePostStatus(BigInteger id, int status) {
        int i =  postDao.changePostStatusById(id, status);
        return FormatResultGenerator.genSuccessResult();
    }

    /**
     * 得到热门的帖子信息
     * @return
     */
    @Override
    public FormatResult<List<PostInfo>> getHotPost() {
        List<PostInfo> list = postDao.getHotPost();
        return FormatResultGenerator.genSuccessResult(list);
    }

    /**
     * 得到用户帖子量，提问量，评论量
     * @param id
     * @return
     */
    @Override
    public FormatResult<Map<String, Object>> getNumberInfoByUserId(BigInteger id) {
        Map<String ,Object> map = postDao.getNumberInfoByUserId(id);
        return FormatResultGenerator.genSuccessResult(map);
    }


}

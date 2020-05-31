package com.hhu.bbs.controller;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.entity.Post;
import com.hhu.bbs.entity.info.PostInfo;
import com.hhu.bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  帖子 Controller
 * @name  PostController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping(value = "post")
public class PostController {
    /**
     * post 默认分页大小10
     */
    private int pageSize = 1000;
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    /**
     * 获取某个分类下的所有帖子
     * @param id 分类id
     * @param page 页数
     * @param sort 排序条件
     * @return
     */
    @RequestMapping(value = "/category/{id}/page/{page}/{sort}", method = RequestMethod.GET)
    public FormatResult<List<PostInfo>> findAllPostByCategoryId(@PathVariable("id")BigInteger id, @PathVariable("page") int page, @PathVariable("sort") String sort){
        int startIndex = (page - 1) * pageSize;
        return postService.findAllPostByCategoryId(id, startIndex, pageSize, sort);
    }

    /**
     * 获取某个用户的所有的post
     * @param id 用户ID
     * @param page 页数
     * @param sort 排序条件
     * @return
     */
    @RequestMapping(value = "user/{id}/page/{page}/{sort}",method = RequestMethod.GET)
    public FormatResult< List<PostInfo>> findAllPostInfoByUserId(@PathVariable("id")BigInteger id, @PathVariable("page") int page,@PathVariable("sort") String sort){
        int startIndex = (page - 1) * pageSize;
        return postService.findAllPostByUserId(id, startIndex, pageSize, sort);
    }

    @RequestMapping(value = "favorite/{id}/page/{page}/{sort}",method = RequestMethod.GET)
    public FormatResult< List<PostInfo>> findAllPostInfoByFavoriteId(@PathVariable("id")BigInteger id, @PathVariable("page") int page,@PathVariable("sort") String sort){
        int startIndex = (page - 1) * pageSize;
        return postService.findAllPostByFavoriteId(id, startIndex, pageSize, sort);
    }

    /**
     * 获取对应ID 的post的详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
    public  FormatResult<Post>findPostById(@PathVariable("id")BigInteger id){
        return postService.getPostById(id);
    }

    /**
     * 发布帖子
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormatResult<BigInteger> addPost(@RequestParam("userId")BigInteger userId,
                                            @RequestParam("categoryId")BigInteger categoryId,
                                            @RequestParam("title") String title,
                                            @RequestParam("content")String content){
        return postService.addPost(userId, categoryId, title, content);
    }
    /**
     * 更新帖子
     * @param userId 用户id
     * @param categoryId 帖子所对应的分类id
     * @param title 帖子标题
     * @param content 帖子内容
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public FormatResult<Object> putPost(@RequestParam("id")BigInteger id,
                                        @RequestParam("userId")BigInteger userId,
                                        @RequestParam("categoryId")BigInteger categoryId,
                                        @RequestParam("title") String title,
                                        @RequestParam("content")String content) {
        return postService.putPost(id, userId, categoryId, title, content);
    }

    /**
     * 获得热门帖子列表
     * @return
     */
    @RequestMapping(value = "hot", method = RequestMethod.GET)
    public FormatResult<List<PostInfo>> getHotPost(){
        return postService.getHotPost();
    }

    /**
     * 根据关键词搜索帖子
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public FormatResult<List<PostInfo>> findAllPostByKeyWord(@RequestParam("keyword")String keyword){
        keyword = "%" + keyword + "%";
        int page = 1;
        int startIndex = (page - 1) * pageSize;
        return postService.findAllPostInfoByKeyword(keyword, startIndex, pageSize, "id");
    }

    /**
     * 获取某个版块的所有帖子
     * @param id 版块id
     * @param page 页数
     * @param sort 排序条件
     * @return
     */
    @RequestMapping(value = "/block/{id}/page/{page}/{sort}", method = RequestMethod.GET)
    public FormatResult<List<PostInfo>> findAllPostByBlockId(@PathVariable("id")BigInteger id, @PathVariable("page") int page, @PathVariable("sort")String sort){
        int startIndex = (page - 1) * pageSize;
        return postService.findAllPostByBlockId(id, startIndex, pageSize, sort);
    }


    /**
     * 获取用户发帖数
     * @param id
     * @return
     */
    @RequestMapping(value = "/number/id/{id}", method = RequestMethod.GET)
    public FormatResult<Map<String, Object>> getNumberInfoByUserId(@PathVariable("id") BigInteger id){
        return postService.getNumberInfoByUserId(id);
    }

    /**
     * 获取某个用户的所有的提问
     * @param id 用户ID
     * @param page 页数
     * @param sort 排序条件
     * @return
     */
    @RequestMapping(value = "question/{id}/page/{page}/{sort}",method = RequestMethod.GET)
    public FormatResult< List<PostInfo>> findQuestionByUserId(@PathVariable("id")BigInteger id, @PathVariable("page") int page,@PathVariable("sort") String sort){
        int startIndex = (page - 1) * pageSize;
        return postService.findQuestionByUserId(id, startIndex, pageSize, sort);
    }
}

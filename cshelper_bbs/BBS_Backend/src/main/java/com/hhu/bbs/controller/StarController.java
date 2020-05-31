package com.hhu.bbs.controller;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;
/**
 *  收藏 Controller
 * @name  StarController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping(value = "star")
public class StarController {
    private StarService starService;

    @Autowired
    public StarController(StarService starService) {
        this.starService = starService;
    }

    /**
     *
     * @param favoriteId
     * @param postId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormatResult<BigInteger> addStar(@RequestParam("favoriteId") BigInteger favoriteId, @RequestParam("postId") BigInteger postId){
        return starService.addStar(postId, favoriteId);
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @RequestMapping(value = "id/{id}", method = RequestMethod.DELETE)
    public FormatResult<BigInteger> delStar(@PathVariable(value = "id")BigInteger id)
    {
        return starService.delStar(id);
    }

    /**
     * 判断帖子是否被用户收藏
     * @param userId
     * @param postId
     * @return
     */
    @RequestMapping(value = "check/{userId}/{postId}")
    public FormatResult<Map<String, Object>> isStar(@PathVariable("userId") BigInteger userId, @PathVariable("postId")BigInteger postId){
        return starService.isStar(userId, postId);
    }

}

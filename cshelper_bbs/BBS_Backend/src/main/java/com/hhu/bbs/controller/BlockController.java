package com.hhu.bbs.controller;

import com.hhu.bbs.entity.Block;
import com.hhu.bbs.entity.info.BlockInfo;
import com.hhu.bbs.service.BlockService;
import com.hhu.bbs.util.format.FormatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  版块 Controller
 * @name  BlockController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping("block")
public class BlockController {
    private BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }


    /**
     * 查询某个block id 下的所有categories
     * @param id
     * @return
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public FormatResult<Block> getBlock(@PathVariable(value = "id")BigInteger id){
        return blockService.getBlockById(id);
    }

    /**
     * 查询所有blockinfo 主页用
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public FormatResult<List<BlockInfo>> findAllBlockInfos(){
        return blockService.findAllBlockInfo();
    }

    /**
     * 版主查询自己的版块信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    public FormatResult<List<Map<String, Object>>> findAllBlockByAdminId(@PathVariable(value = "id") BigInteger id){
        return blockService.findAllBlockByAdminId(id);
    }

}

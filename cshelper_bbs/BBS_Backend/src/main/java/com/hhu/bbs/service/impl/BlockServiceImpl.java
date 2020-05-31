package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.BlockDao;
import com.hhu.bbs.entity.Block;
import com.hhu.bbs.entity.info.BlockInfo;
import com.hhu.bbs.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  版块 Service
 * @name  BlockServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class BlockServiceImpl implements BlockService {
    private BlockDao blockDao;

    @Autowired
    public BlockServiceImpl(BlockDao blockDao){
        this.blockDao = blockDao;
    }

    /**
     * 使用Block id 检索对应block的信息 （包括block下的categorys)
     * @param id
     * @return
     */
    @Override
    public FormatResult<Block> getBlockById(BigInteger id) {
        Block block = blockDao.getBlockById(id);
        if (block == null){
            return FormatResultGenerator.genErrorResult("block id 不存在");
        }
        return FormatResultGenerator.genSuccessResult(block);
    }

    /**
     * 检索所有版块信息 （不包括categorys)
     * @return
     */
    @Override
    public FormatResult<List<BlockInfo>> findAllBlockInfo() {
        List<BlockInfo> blockInfos = blockDao.findAllBlockInfo();
        if (blockInfos == null){
            return FormatResultGenerator.genErrorResult("block id 不存在");
        }
        return FormatResultGenerator.genSuccessResult(blockInfos);
    }

    /**
     * 版主查询自己管理的版块
     * @param id
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAllBlockByAdminId(BigInteger id) {
        List<Map<String, Object>> list = blockDao.findAllByAdminId(id);
        return FormatResultGenerator.genSuccessResult(list);
    }
}

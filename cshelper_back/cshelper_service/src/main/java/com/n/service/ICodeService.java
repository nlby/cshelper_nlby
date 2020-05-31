package com.n.service;

import com.n.domain.Code;

import java.util.List;

public interface ICodeService {
    // 添加code
    public void save(Code code) throws Exception;

    // 查询某用户所有code信息 分页查询
    public List<Code> findAllByUserId(String userId, int page, int size) throws Exception;

    // 根据id查询code
    public Code findById(String id) throws Exception;
}

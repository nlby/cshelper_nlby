package com.n.service.impl;

import com.github.pagehelper.PageHelper;
import com.n.dao.ICodeDao;
import com.n.domain.Code;
import com.n.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CodeServiceImpl implements ICodeService {
    @Autowired
    private ICodeDao codeDao;
    @Override
    public void save(Code code) throws Exception {
        int rownum = codeDao.getCount(code.getUserId());
        code.setRownum(++rownum);
        codeDao.saveCode(code);
    }

    @Override
    public List<Code> findAllByUserId(String userId, int page, int size) throws Exception {
        PageHelper.startPage(page, size);
        return codeDao.findAll(userId);
    }

    @Override
    public Code findById(String id) throws Exception {
        return codeDao.findById(id);
    }
}

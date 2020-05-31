package com.n.service;

import com.n.domain.Blog;
import com.n.domain.Note;

import java.util.List;

public interface INoteService {
    // 同步移动端笔记表中的笔记到web端笔记表
    public void syncNote(String userId) throws Exception;

    // 查询某用户所有笔记信息 分页查询
    public List<Note> findAllByUserId(String userId, int page, int size) throws Exception;

    // 根据id查询笔记
    public Note findById(String id) throws Exception;
}

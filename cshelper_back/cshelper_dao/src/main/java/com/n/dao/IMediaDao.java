package com.n.dao;

import com.n.domain.Note;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IMediaDao {

    // 查询所有剧集名称列表
    @Select("select aname from imo order by id desc")
    public List<String> findAll() throws Exception;

    // 查询所有剧集名称列表
    @Select("select links from imo where aname=#{aname}")
    public String findLinksByAname(String aname) throws Exception;
}

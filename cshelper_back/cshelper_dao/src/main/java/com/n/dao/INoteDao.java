package com.n.dao;

import com.n.domain.Blog;
import com.n.domain.BookMark;
import com.n.domain.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface INoteDao {

    // 根据userId查询移动端笔记表中某一用户的所有笔记
    @Select("select * from cshelper_note where userId = #{userId}")
    public List<Note> findAllByUserIdFromMobile(String userId) throws Exception;

    // 添加笔记  此处更新的逻辑  完全同步时，对同一条记录，笔记的标题，内容，更新时间要做更新
    @Insert("insert into cshelper_note_web(id,rid,title,body,createAt,updateAt,userId) values(#{id},#{rid},#{title},#{body},#{createAt},#{updateAt},#{userId}) ON DUPLICATE KEY UPDATE title=#{title},body=#{body},updateAt=#{updateAt}")
    public void saveNote(Note note) throws Exception;

    // 根据userId查询web端笔记表中某一用户的所有笔记
    @Select("select * from cshelper_note_web where userId = #{userId} order by rid")
    public List<Note> findAll(String userId) throws Exception;

    // 根据id查询笔记
    @Select("select * from cshelper_note_web where id = #{id}")
    public Note findById(String id) throws Exception;
}

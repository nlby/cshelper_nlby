package dao;

import domain.BookMark;
import domain.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface INoteDao {


    //添加笔记
    @Insert("insert into cshelper_note(id,rid,title,body,createAt,updateAt,userId) values(#{id},#{rid},#{title},#{body},#{createAt},#{updateAt},#{userId})")
    public void addNote(Note note) throws Exception;

    //更新笔记
    @Update("update cshelper_note set title=#{title},body=#{body},updateAt=#{updateAt} where createAt=#{createAt}")
    public void updateNote(Note note) throws Exception;

    //根据创建时间和userId获取某用户的某个笔记
    @Select("select * from cshelper_note where createAt=#{createAt} and userId=#{userId}")
    public Note findNoteByCreateAt(@Param("createAt")String createAt, @Param("userId")String userId) throws Exception;

    //根据userId获取某一用户的笔记数
    @Select("select count(*) from cshelper_note where userId = #{userId}")
    public int getCount(String userId) throws Exception;

}

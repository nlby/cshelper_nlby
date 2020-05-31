package com.n.dao;

import com.n.domain.BookMark;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookMarkDao {


    // 根据userId查询移动端书签表中某一用户的所有书签
    @Select("select * from cshelper_bookmark where userId = #{userId}")
    public List<BookMark> findAllByUserIdFromMobile(String userId) throws Exception;

    //添加书签  每次同时完全同步一次因此会有主键重复的情况，因为书签变动几乎没有，因此更新id表示几乎不变
    @Insert("insert into cshelper_bookmark_web(id,rid,title,url,type,createAt,userId,rownum) values(#{id},#{rid},#{title},#{url},#{type},#{createAt},#{userId},#{rownum}) ON DUPLICATE KEY UPDATE id = #{id}")
    public void saveBookMark(BookMark bookMark) throws Exception;

    // 根据userId查询web端书签表中某一用户的所有书签
    @Select("select * from cshelper_bookmark_web where userId = #{userId} order by rownum")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "type",column = "type"),
            @Result(property = "url",column = "url"),
            @Result(property = "createAt",column = "createAt"),
            @Result(property = "userId",column = "userId"),
            @Result(property = "rid",column = "rid"),
            @Result(property = "rownum",column = "rownum"),
    })
    public List<BookMark> findAll(String userId) throws Exception;
}

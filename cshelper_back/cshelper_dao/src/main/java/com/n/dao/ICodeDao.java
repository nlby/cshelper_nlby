package com.n.dao;

import com.n.domain.Code;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICodeDao {

    // 根据userId查询web端code表中某一用户的所有code
    @Select("select * from cshelper_code where userId = #{userId} order by rownum")
    public List<Code> findAll(String userId) throws Exception;

    // 根据userId获取某一用户的code数
    @Select("select count(*) from cshelper_code where userId = #{userId}")
    public int getCount(String userId) throws Exception;

    // 添加code
    @Insert("insert into cshelper_code(id,title,typef,types,content,createAt,updateAt,userId,rownum) values(#{id},#{title},#{typef},#{types},#{content},#{createAt},#{updateAt},#{userId},#{rownum}) ON DUPLICATE KEY UPDATE title=#{title},typef=#{typef},types=#{types},content=#{content},updateAt=#{updateAt}")
    public void saveCode(Code code) throws Exception;

    // 根据id查询code
    @Select("select * from cshelper_code where id = #{id}")
    public Code findById(String id) throws Exception;
}

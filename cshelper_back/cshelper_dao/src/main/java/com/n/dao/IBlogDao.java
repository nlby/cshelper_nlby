package com.n.dao;

import com.n.domain.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogDao {

    // 根据userId查询web端blog表中某一用户的所有blog
    @Select("select * from cshelper_blog where userId = #{userId} order by rownum")
    public List<Blog> findAll(String userId) throws Exception;

    // 根据userId获取某一用户的blog数
    @Select("select count(*) from cshelper_blog where userId = #{userId}")
    public int getCount(String userId) throws Exception;

    // 添加blog
    @Insert("insert into cshelper_blog(id,title,typef,types,content,createAt,updateAt,userId,rownum) values(#{id},#{title},#{typef},#{types},#{content},#{createAt},#{updateAt},#{userId},#{rownum}) ON DUPLICATE KEY UPDATE title=#{title},typef=#{typef},types=#{types},content=#{content},updateAt=#{updateAt}")
    public void saveBlog(Blog blog) throws Exception;

    // 根据id查询blog
    @Select("select * from cshelper_blog where id = #{id}")
    public Blog findById(String id) throws Exception;
}

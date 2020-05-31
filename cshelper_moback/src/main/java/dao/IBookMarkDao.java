package dao;

import domain.BookMark;
import domain.User;
import org.apache.ibatis.annotations.*;

public interface IBookMarkDao {

    //添加书签
    @Insert("insert into cshelper_bookmark(id,rid,title,url,type,createAt,userId) values(#{id},#{rid},#{title},#{url},#{type},#{createAt},#{userId})")
    public void addBookMark(BookMark bookMark) throws Exception;


    //根据rid，useId，title，url，type查询书签  其实这里根据rid判断有些bug 但为对照测试暂不处理
    @Select("select * from cshelper_bookmark where  userId=#{userId} and title=#{title} and url=#{url} and type=#{type}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "rid", column = "rid"),
            @Result(property = "title", column = "title"),
            @Result(property = "url", column = "url"),
            @Result(property = "type", column = "type"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "userId", column = "userId"),
    })
    public BookMark findBookMarkById(@Param("userId")String userId, @Param("title")String title,
                                     @Param("url")String url, @Param("type")String type) throws Exception;

    // 根据userId获取某一用户的书签数
    @Select("select count(*) from cshelper_bookmark where userId = #{userId}")
    public int getCount(String userId) throws Exception;


    //根据rid，useId查询书签
    @Select("select * from cshelper_bookmark where rid=#{rid} and userId=#{userId} ")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "rid", column = "rid"),
            @Result(property = "title", column = "title"),
            @Result(property = "url", column = "url"),
            @Result(property = "type", column = "type"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "userId", column = "userId"),
    })
    public BookMark findBookMarkById2(@Param("rid")String rid, @Param("userId")String userId) throws Exception;

}

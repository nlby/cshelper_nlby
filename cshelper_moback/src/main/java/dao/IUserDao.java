package dao;

import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {
    //根据用户名查询用户
    @Select("select * from cshelper_user where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "mail", column = "mail"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
    })
    public User findUserByName(String username) throws Exception;

    //根据邮箱查询用户
    @Select("select * from cshelper_user where mail=#{mail}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "mail", column = "mail"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
    })
    public List<User> findUserByMail(String mail) throws Exception;

    //添加用户
    @Insert("insert into cshelper_user(id, username,password,phone,mail,createAt,updateAt) values(#{id},#{username},#{password},#{phone},#{mail},#{createAt},#{updateAt})")
    public void addUser(User user) throws Exception;


    /**
     * 在论坛表中插入一条新用户记录
     * @param username
     * @param password
     * @param nickname
     * @return  插入记录id
     */
    @Insert("insert into user (username, password, nickname) value (#{username}, #{password}, #{nickname})")
    int addBBSUser(@Param("username") String username, @Param("nickname") String nickname, @Param("password") String password);

    // 获取用户数
    @Select("select count(*) from cshelper_user")
    public int getCount() throws Exception;


}

package com.hhu.bbs.dao;

import com.hhu.bbs.entity.info.UserInfo;
import com.hhu.bbs.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    /**
     * 插入一条新用户记录
     * @param username
     * @param password
     * @param nickname
     * @return  插入记录id
     */
    @Insert("insert into user (username, password, nickname) value (#{username}, #{password}, #{nickname})")
    int addUser(@Param("username") String username, @Param("password") String password, @Param("nickname") String nickname);

    /**
     * 判断用户昵称是否被占用
     * @param nickname
     * @return 存在则返回，否则为null
     */
    @Select("select id from user where nickname = #{nickname}")
    String isNicknameExist(@Param("nickname") String nickname);

    /**
     * 判断用户账号是否被注册
     * @param username
     * @return 存在则返回，否则为null
     */
    @Select("select id from user where username = #{username}")
    String isUsernameExist(@Param("username") String username);


    /**
     * 使用用户名和密码查询数据库
     * @param username
     * @param password
     * @return 对应用户信息
     */
    UserInfo getUserInfoByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户ID 查询信息
     * @param id
     * @return 用户信息
     */
    UserInfo getUserInfoById(@Param("id") BigInteger id);

    /**
     * 根据用户ID 查询信息
     * @param id
     * @return 用户信息
     */
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") BigInteger id);

    /**
     * 更新用户信息
     * @param id
     * @param avatar
     * @param gender
     * @param workplace
     * @param description
     * @return
     */
    @Update("update user set avatar = #{avatar}, gender = #{gender}, workplace = #{workplace}, description = #{description} where id = #{id}")
    int putUserInfo(@Param("id")BigInteger id, @Param("avatar")String avatar, @Param("gender")String gender, @Param("workplace")String workplace, @Param("description")String description);


    /**
     * 查看所有用户列表（不需要连表， 需要startIndex 和pageSize参数)
     * 管理员
     * @return
     */
    @Select("select * from user limit #{startIndex}, #{pageSize}")
    List<Map<String, Object>> findAll(@Param("startIndex") int  startIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊匹配查询用户
     * @param pattern
     * @return
     */
    @Select("select * from user where nickname like #{nickname}")
    List<Map<String, Object>> findAllByNickname(@Param("nickname") String pattern);

    /**
     * 将用户删除，只是使status = 0
     * @param id
     * @return 成功与否
     */
    @Update("update user set status = #{status} where id = #{id}")
    int changeStatusById(@Param("id") BigInteger id, @Param("status") int status);

    @Select("select count(id) from user")
    int getUserSum();

    @Update("update user set last_login_time = sysdate() where username = #{username};")
     int updateLastLoginTime(@Param("username") String username);

    /**
     * 在用户注册时设置token
    */
    @Update("update user set token = sysdate() where id = #{id};")
    int setToken(@Param("token")String token, @Param("id")BigInteger id);
}
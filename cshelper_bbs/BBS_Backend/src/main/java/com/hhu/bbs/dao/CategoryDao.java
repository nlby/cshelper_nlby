package com.hhu.bbs.dao;

import com.hhu.bbs.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface CategoryDao {

    /**
     * 查询所有分类列表
     * @return
     */
    @Select("select * from category where status = 1")
    List<Category> findAll();

    /**
     * 查询某版块下的所有category
     * @return
     */
    @Select("select * from category where status = 1 and block_id = #{blockId}")
    List<Category> findAllByBlockId(@Param("blockId") BigInteger id);

    /**
     * 查询某个分类信息
     * @param id
     * @return
     */
    @Select("select category.id, category.name, category.description, block_id, block.name block_name from category, block where category.id = #{id} and category.block_id = block.id and category.status = 1;")
    Map<String, Object> getCategoryById(@Param("id")BigInteger id);

    /**
     * 添加分类
     * @param id 版块对应block id
     * @param name
     * @param description
     * @return
     */
    @Insert("insert into category (block_id, name, description) value (#{blockId}, #{name}, #{description})")
    int addCategory(@Param(value = "blockId")BigInteger id, @Param(value = "name")String name, @Param(value = "description")String description);

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Update("update category set status = 0 where id = #{id}")
    int delCategory(@Param(value = "id") BigInteger id);
}

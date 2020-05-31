package com.n.domain;

import java.util.Calendar;
import java.util.UUID;

public class Blog {
    private String id;  //日志id
    private String title;  //日志标题
    private String typef;    //日志一级分类
    private String types;   //日志二级分类
    private String content; //日志内容
    private String createAt;  //创建时间
    private String updateAt;  //更新时间
    private String userId;
    private int rownum;  //分页需要

    public Blog() {
    }

    public Blog(String title, String typef, String types, String content, String userId) {
          UUID uuid = UUID.randomUUID();
          this.id = uuid+"";
          this.title = title;
          this.typef = typef;
          this.types = types;
          this.content = content;
          Calendar calendar = Calendar.getInstance();
          this.createAt = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                  + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";
          this.updateAt = createAt;
          this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypef() {
        return typef;
    }

    public void setTypef(String typef) {
        this.typef = typef;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }
}

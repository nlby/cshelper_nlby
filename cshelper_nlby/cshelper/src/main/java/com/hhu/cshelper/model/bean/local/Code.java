package com.hhu.cshelper.model.bean.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/** 代码类
 * @name  Code
 * @description  代码类
 * @author  nlby
 * @date  2020/4/29
 */
@Entity
public class Code {
    @Id(autoincrement = true)
    private Long id;  //本地id

    private String rid;  //服务器端id
    private String title;  //标题
    private String body;  //内容
    private String createAt;  //创建时间
    private String updateAt;  //更新时间
    private String userId;  //用户id

    @Generated(hash = 364261929)
    public Code() {
    }

    @Generated(hash = 973812147)
    public Code(Long id, String rid, String title, String body, String createAt, String updateAt, String userId) {
        this.id = id;
        this.rid = rid;
        this.title = title;
        this.body = body;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
}

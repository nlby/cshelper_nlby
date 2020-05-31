package com.n.domain;

import com.n.domain.BaseBean;

public class Note extends BaseBean {
    private String id;  //笔记id 服务器端生成
    private String rid;  //移动端id
    private String title;  //笔记标题
    private String body;    //笔记内容
    private String createAt;  //创建时间
    private String updateAt;  //更新时间
    private String userId;  //用户id

    public Note() {
    }

    public Note(String rid, String title, String body, String createAt, String updateAt, String userId) {
        this.rid = rid;
        this.title = title;
        this.body = body;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}

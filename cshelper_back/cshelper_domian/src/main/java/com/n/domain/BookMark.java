package com.n.domain;

public class BookMark extends BaseBean{
    private String id;  //书签id 服务器端生成
    private String rid;  //移动端id
    private String title;  //网页标题
    private String url;    //网页url
    private String type;   //书签类型
    private String createAt;  //创建时间
    private String userId;  //用户id
    private int rownum; //分页需要

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public BookMark(String rid, String title, String url, String type, String createAt, String userId) {
//        UUID uuid = UUID.randomUUID();  // 这里不能直接写 必须保证id为null insert不成功就是null
//        this.id = uuid+"";
        this.rid = rid;
        this.title = title;
        this.url = url;
        this.type = type;
        this.createAt = createAt;
        this.userId = userId;
    }

    public BookMark() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}

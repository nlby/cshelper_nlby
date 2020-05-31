package com.hhu.cshelper.model.bean.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Calendar;

/** 书签类
 * @name  BookMark
 * @description  书签类
 * @author  nlby
 * @date  2020/4/29
 */
@Entity
public class BookMark {
    @Id(autoincrement = true)
    private Long id;  //本地id

    private String rid;  //服务器端id
    private String title;  //标题
    private String url;  //链接
    private String type;  //类别
    private String createAt;  //创建时间
    private String userId;  //用户id

    public BookMark() {
    }

    public BookMark(String title, String url, String type) {
        this.title = title;
        this.url = url;
        this.type = type;
        Calendar calendar = Calendar.getInstance();
        this.createAt = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
//                calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日"
//                + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";
    }

    @Generated(hash = 112491183)
    public BookMark(Long id, String rid, String title, String url, String type, String createAt, String userId) {
        this.id = id;
        this.rid = rid;
        this.title = title;
        this.url = url;
        this.type = type;
        this.createAt = createAt;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

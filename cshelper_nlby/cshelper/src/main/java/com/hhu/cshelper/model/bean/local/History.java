package com.hhu.cshelper.model.bean.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Calendar;

/** 历史记录类
 * @name  History
 * @description  历史记录类
 * @author  nlby
 * @date  2020/4/29
 */
@Entity
public class History {
    @Id(autoincrement = true)
    private Long id;  //本地id

    private String title;  //标题
    private String url;  //链接
    private String createAt;  //创建时间
    private String dayTime;  //具体一天中的时间

    public History() {
    }

    public History(String title, String url) {
        this.title = title;
        this.url = url;
        Calendar calendar = Calendar.getInstance();
        this.createAt = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        this.dayTime = calendar.get(Calendar.HOUR_OF_DAY) + "时";
    }

    @Generated(hash = 211235161)
    public History(Long id, String title, String url, String createAt, String dayTime) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.createAt = createAt;
        this.dayTime = dayTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}

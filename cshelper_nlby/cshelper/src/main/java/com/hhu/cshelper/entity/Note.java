package com.hhu.cshelper.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;


import com.hhu.cshelper.utils.TimeUtil;

/** note实体的一些属性和操作
 * @name  Note
 * @description note实体的一些属性和操作
 * @author  dyh
 * @date  2020/4/29
 */

public class Note implements Parcelable , Comparable<Note> {   //序列化接口

    private int id;
    private String title;
    private String content;
    private String date;
    private String address;
    private long timestamp;
    private long lastModify;
    //private int isWasted;
    private String userId; // nlby: 用于网络通信

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @name  Note
     * @description 获得日期用于显示，获得时间戳用于排序
     * @param
     * @return
     * @date  2020/4/29
     */
    public Note(){
        date = TimeUtil.getDataTime("yyyy/MM/dd HH:mm");
//        timestamp =System.currentTimeMillis();
    }



    public Note(String title, String content, String date, String address, long lastModify) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.address = address;
        this.lastModify = lastModify;
    }


    public Note(Parcel in) {

        id = in.readInt();
        title = in.readString();
        content = in.readString();
        date = in.readString();
        address = in.readString();
        timestamp = in.readLong();
        lastModify = in.readLong();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLastModify() {
        return lastModify;
    }

    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }



    /**
     * @name  paraseString2Note
     * @description 把文本转化成note格式
     * @param
     * @return
     * @date  2020/4/29
     */
    public static Note paraseString2Note() {
        Note note = null;
        return note;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(address);
        parcel.writeString(date);
        parcel.writeLong(timestamp);
        parcel.writeLong(lastModify);
    }

    @Override
    public int compareTo(Note note) {
        return (int)(this.timestamp - note.timestamp);
    }


}

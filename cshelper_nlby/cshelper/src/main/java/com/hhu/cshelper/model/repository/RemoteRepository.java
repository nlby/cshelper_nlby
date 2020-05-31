package com.hhu.cshelper.model.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhu.cshelper.activity.web.BaseWebActivity;
import com.hhu.cshelper.common.Constants;
import com.hhu.cshelper.database.NoteDatabaseHelper;
import com.hhu.cshelper.entity.Note;
import com.hhu.cshelper.model.bean.local.BookMark;
import com.hhu.cshelper.model.bean.remote.BaseBean;
import com.hhu.cshelper.net.OkHttpUtils;
import com.hhu.cshelper.utils.TimeUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/** 通过网络请求操作远程数据库，实现数据上传等操作
 * @name  RemoteRepository
 * @description  通过网络请求操作远程数据库，实现数据上传等操作
 * @author  nlby
 * @date  2020/4/29
 */
public class RemoteRepository {

    private static final String TAG = "RemoteRepository";
    private static volatile RemoteRepository sInstance;
    private BaseWebActivity web;  // 调用回调方法的activity

    private int count = 0;  // 响应成功计数器
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    private int bookMarkNum = 0;  // 上传书签记录数，请求数

    private int noteNum = 0;  // 上传笔记记录数，请求数

    private RemoteRepository() {
    }

    public static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (RemoteRepository.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRepository();
                }
            }
        }
        return sInstance;
    }

    /**
     *  同步书签
     */
    public void syncBookMark(String userid, BaseWebActivity web) {
        this.web = web;
        List<BookMark> bookMarks = LocalRepository.getInstance().getAllBookMarkList();
        bookMarkNum = bookMarks.size();
        for (int i = 0; i < bookMarks.size(); i++) {
                BookMark bookMark = bookMarks.get(i);
                bookMark.setUserId(userid);
                uploadBookMark(bookMark);
        }
        web.syncProcess(count, bookMarkNum);
    }

    /**
     *  单个书签记录的上传
     */
    private void uploadBookMark(BookMark bookMark) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", bookMark.getUserId());
        params.put("rid", bookMark.getId().toString());
        params.put("title", bookMark.getTitle());
        params.put("url", bookMark.getUrl());
        params.put("type", bookMark.getType());
        params.put("createAt", bookMark.getCreateAt());
        OkHttpUtils.getInstance().post(Constants.URL_BOOKMARK_ADD, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                web.syncFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                System.out.println(s);
                if (s.contains("400") || s.contains("500") || s.contains("404")) {
                    web.syncFail(new Throwable("服务器响应错误，该问题可能是书签的标题或url中含有非法字符，请删除该书签后重试"));
                    return;
                }
                Type type = new TypeToken<BaseBean>(){}.getType();  // 这个泛型的User写错了，但不影响功能，留作参考对照
                BaseBean baseBean = new Gson().fromJson(s, type);
                if (baseBean.getStatus() == 200) {
                     web.syncFail(new Throwable(baseBean.getMessage())); // 有一个请求响应失败整个过程就失败
                } else {
                    count++;   // 响应成功计数++
                    web.syncProcess(count, bookMarkNum);
                }
            }
        });
    }



    /**
     *  同步笔记
     */
    public void syncNote(String userid, BaseWebActivity web) {
        this.web = web;
        List<Note> notes = NoteDatabaseHelper.getInstance().getAllNotes();
        noteNum = notes.size();
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            note.setUserId(userid);
            uploadNote(note);
        }
        web.syncProcess(count, noteNum);
    }

    /**
     *  单个笔记记录的上传
     */
    private void uploadNote(Note note) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", note.getUserId());
        params.put("rid", Integer.toString(note.getId()));
        params.put("title", note.getTitle());
        params.put("body", note.getContent());
        params.put("createAt", TimeUtil.convert(note.getTimestamp()));
        params.put("updateAt", TimeUtil.convert(note.getLastModify()));
        OkHttpUtils.getInstance().post(Constants.URL_NOTE_ADD, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                web.syncFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (s.contains("400") || s.contains("500") || s.contains("404")) {
                    web.syncFail(new Throwable("服务器响应错误，请重试"));
                    return;
                }
                Type type = new TypeToken<BaseBean>(){}.getType();  // 此处相较以上换位basebean
                BaseBean baseBean = new Gson().fromJson(s, type);
                if (baseBean.getStatus() == 200) {
                    web.syncFail(new Throwable(baseBean.getMessage())); // 有一个请求响应失败整个过程就失败
                } else {
                    count++;   // 响应成功计数++
                    web.syncProcess(count, noteNum);
                }
            }
        });
    }

}

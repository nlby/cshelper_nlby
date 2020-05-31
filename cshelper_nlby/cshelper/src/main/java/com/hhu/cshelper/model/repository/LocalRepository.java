package com.hhu.cshelper.model.repository;


import android.database.Cursor;


import com.hhu.cshelper.model.bean.local.BookMark;
import com.hhu.cshelper.model.bean.local.History;
import com.hhu.cshelper.model.bean.local.Code;
import com.hhu.cshelper.model.gen.BookMarkDao;
import com.hhu.cshelper.model.gen.DaoSession;
import com.hhu.cshelper.model.gen.HistoryDao;
import com.hhu.cshelper.model.gen.CodeDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**  本地数据库的相关操作，处理本地数据的存储
 * @name  LocalRepository
 * @description  本地数据库的相关操作，处理本地数据的存储
 * @author  nlby
 * @date  2020/4/29
 */
public class LocalRepository {

    private static volatile LocalRepository sInstance;
    private DaoSession mSession;

    private LocalRepository() {
        mSession = DaoDbHelper.getInstance().getSession();
    }

    public static LocalRepository getInstance() {
        if (sInstance == null) {
            synchronized (LocalRepository.class) {
                if (sInstance == null) {
                    sInstance = new LocalRepository();
                }
            }
        }
        return sInstance;
    }

/**
 *   代码相关数据库操作            start
 */

    /**
     *   创建笔记
     */
    public Long saveCode(Code code) {
        return mSession.getCodeDao().insert(code);
    }



    /**
     *   创建笔记 根据需求封装
     */
    public void createCode(String title, String body, String rid, String userId) {
        Code code = new Code();
        code.setTitle(title);
        code.setBody(body);
        Calendar calendar = Calendar.getInstance();
        String createAt = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";
        String updateAt = createAt;
        code.setCreateAt(createAt);
        code.setUpdateAt(updateAt);
        code.setRid(rid);
        code.setUserId(userId);
        this.saveCode(code);
    }

    /**
     *   根据id获取代码笔记
     */
    public Code getCodeById(long id) {
        return mSession.getCodeDao().queryBuilder()
                .where(CodeDao.Properties.Id.eq(id)).unique();
    }

    /**
     * 获取所有代码笔记 Cusor
     */
    public Cursor getAllCodes() {
        return  mSession.getCodeDao().queryBuilder().buildCursor().query();

    }

    /**
     *   获取所有代码笔记 List
     */
    public List<Code> getAllCodesList() {
        return  mSession.getCodeDao().queryBuilder().list();

    }


    /**
     *   更新代码笔记
     */
    public void updateCode(Code code) {
        mSession.getCodeDao().update(code);
    }

    /**
     *   更新代码笔记 按需封装
     */
    public void updateCode(long rowId, String title, String body) {
        Code code = this.getCodeById(rowId);
        code.setTitle(title);
        code.setBody(body);
        Calendar calendar = Calendar.getInstance();
        String updateAt = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";
        code.setUpdateAt(updateAt);
        this.updateCode(code);
    }


    /**
     *  根据id删除代码笔记
     */
    public void deleteCodeById(long id) {
        mSession.getCodeDao().deleteByKey(id);
    }

/**
 *   代码相关数据库操作            end
 */



/**
 *   书签相关数据库操作            start
 */

    /**
     *   创建书签
     */
    public Long saveBookMark(BookMark bookMark) {
        Long result = -1l;
        List<BookMark> bookMarks = getAllBookMarkList();
        for (BookMark bookMark1: bookMarks) {
            if (bookMark.getTitle().equals(bookMark1.getTitle()) && bookMark.getUrl().equals(bookMark1.getUrl()) && bookMark.getType().equals(bookMark1.getType())) {
                return result;
            }
        }
        return mSession.getBookMarkDao().insert(bookMark);
    }

    /**
     *   获取所有书签
     */
    public List<BookMark> getAllBookMarkList() {
        return  mSession.getBookMarkDao().queryBuilder().list();

    }

    /**
     *   获取所有书签类型
     */
    public HashSet<String> getTypesOfBookMark() {
        List<BookMark> bookMarks =  mSession.getBookMarkDao().queryBuilder().list();
        HashSet<String> types = new HashSet<>();
        for (BookMark bookMark: bookMarks) {
            types.add(bookMark.getType());
        }
        return types;
    }

    /**
     *   根据类型获取书签
     */
    public List<BookMark> getBookMarkByType(String type) {
        List<BookMark> bookMarks = mSession.getBookMarkDao().queryBuilder()
                .where(BookMarkDao.Properties.Type.eq(type)).list();
        Collections.reverse(bookMarks);
        return bookMarks;
    }

    /**
     *  获取每个类型对应的记录数
     */
    public List<String> getNumOfType(List<String> typeList) {
        List<String> starNumList = new ArrayList<>();
        for (String type: typeList) {
            int num = mSession.getBookMarkDao().queryBuilder()
                    .where(BookMarkDao.Properties.Type.eq(type)).list().size();
            starNumList.add(new Integer(num).toString());
        }
        return starNumList;
    }

    /**
     *   根据id删除书签
     */
    private void deleteBookMarkById(Long id) {
        mSession.getBookMarkDao().deleteByKey(id);
    }

    /**
     *   根据id集合批量删除书签
     */
    public void deleteBookMarks(List<Long> ids) {
        for (Long id: ids) {
            deleteBookMarkById(id);
        }
    }

/**
 *   书签相关数据库操作            end
 */



/**
 *   历史记录相关数据库操作        start
 */
    /**
     *   创建历史记录
     */
    public Long saveHistory(History history) {
        Long result = -1l;
        List<History> histories = getAllHistoryList();
        for (History history1: histories) {
            if (history.getTitle().equals(history1.getTitle()) && history.getUrl().equals(history1.getUrl())) {
                return result;
            }
        }
        return mSession.getHistoryDao().insert(history);
    }

    /**
     *   获取所有历史记录
     */
    public List<History> getAllHistoryList() {
        return  mSession.getHistoryDao().queryBuilder().list();

    }

    /**
     *   获取所有历史记录日期
     */
    public List<String> getDatesOfHistory() {
        List<History> histories =  mSession.getHistoryDao().queryBuilder().list();
        HashSet<String> dateSet = new HashSet<>();
        for (History history: histories) {
            dateSet.add(history.getCreateAt());
        }
        List<String> dates = new ArrayList<>(dateSet);
        Collections.reverse(dates);
        return dates;
    }

    /**
     *   根据日期获取历史记录
     */
    public List<History> getHistoryByDate(String date) {
        List<History> histories = mSession.getHistoryDao().queryBuilder()
                .where(HistoryDao.Properties.CreateAt.eq(date)).list();
        Collections.reverse(histories);
        return histories;
    }

    /**
     *   获取每个日期对应的记录数
     */
    public List<String> getNumOfDate(List<String> dateList) {
        List<String> historyNumList = new ArrayList<>();
        for (String date: dateList) {
            int num = mSession.getHistoryDao().queryBuilder()
                    .where(HistoryDao.Properties.CreateAt.eq(date)).list().size();
            historyNumList.add(new Integer(num).toString());
        }
        return historyNumList;
    }

    /**
     *   根据id删除历史记录
     */
    private void deleteHistoryById(Long id) {
        mSession.getHistoryDao().deleteByKey(id);
    }

    /**
     *   根据id集合批量删除历史记录
     */
    public void deleteHistoriess(List<Long> ids) {
        for (Long id: ids) {
            deleteHistoryById(id);
        }
    }

/**
 *   历史记录相关数据库操作        end
 */

}

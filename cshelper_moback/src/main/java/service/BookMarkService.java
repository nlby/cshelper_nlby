package service;

import dao.IBookMarkDao;
import domain.BookMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class BookMarkService {
    @Autowired
    private IBookMarkDao bookMarkDao;

    /**
     * 添加书签
     * @param
     */
    public synchronized void insertBookMark(BookMark bookMark) throws Exception{
        BookMark bookMark2 = bookMarkDao.findBookMarkById(bookMark.getUserId(), bookMark.getTitle(),
                bookMark.getUrl(), bookMark.getType());
        if (bookMark2 != null) {
            bookMark.setId(bookMark2.getId());
            return;
        }
        UUID uuid = UUID.randomUUID();
        bookMark.setId(uuid+"");
        int count = bookMarkDao.getCount(bookMark.getUserId());
        bookMark.setRid(Integer.toString(++count));
        bookMarkDao.addBookMark(bookMark);
    }
}

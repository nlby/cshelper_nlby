package controller;

import domain.BaseBean;
import domain.BookMark;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.BookMarkService;
import utils.ConvertUtils;

@Controller
@RequestMapping("/bookmark")
public class BookMarkController {
    @Autowired
    private BookMarkService bookMarkService;

    /**
     * 添加书签
     *
     * @param
     * @param rid
     * @param title
     * @param url
     * @param type
     * @param createAt
     * @param userId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    public BaseBean addBookMark(@Param("rid") String rid, @Param("title") String title,
                            @Param("url") String url, @Param("type") String type, @Param("createAt")String createAt,
                            @Param("userId") String userId) throws Exception{
        BookMark bookMark = new BookMark(rid, title, url, type, createAt, userId);
        bookMarkService.insertBookMark(bookMark);
        if (bookMark.getId() == null)
            return new BaseBean().fail();
        return new BaseBean().success();
    }
}

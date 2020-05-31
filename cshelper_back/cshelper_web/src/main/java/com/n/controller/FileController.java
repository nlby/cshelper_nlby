package com.n.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传
     * @throws Exception
     */
    @RequestMapping(value="/uploadAvatar.do")
    public String uploadAvatar(HttpServletRequest request) throws Exception {
        // 先获取到要上传的文件目录
        String path = request.getSession().getServletContext().getRealPath("/uploads");
        // 创建File对象，一会向该路径下上传文件
        File file = new File(path);
        // 判断路径是否存在，如果不存在，创建该路径
        if(!file.exists()) {
            file.mkdirs();
        }
        // 创建磁盘文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        // 解析request对象
        List<FileItem> list = fileUpload.parseRequest(request);
        // 遍历
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        for (FileItem fileItem : list) {
            // 判断文件项是普通字段，还是上传的文件
            if(fileItem.isFormField()) {

            }else {
                // 上传文件项
                // 获取到上传文件的名称
                String filename = fileItem.getName();
                // 上传文件
                fileItem.write(new File(file, userId));
                // 删除临时文件
                fileItem.delete();
            }
        }

        return "main";
    }

}

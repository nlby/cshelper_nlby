package com.hhu.bbs.controller;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 *  上传图片 Controller
 * @name  ImgController
 * @author  nlby
 * @date  2020/5/14
 */
@RestController
@RequestMapping("upload")
public class ImgController {
    private String ip = "123.56.15.54";
    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public FormatResult<String> uploadAvatar(@RequestParam(value = "name") String name, @RequestParam(value="file")MultipartFile file, HttpServletRequest request) {
//        name = System.currentTimeMillis() + "";
        String path = request.getSession().getServletContext().getRealPath("/upload/avatar/");
        File filePath = new File(path);
        if(!filePath.exists()) {
            filePath.mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File( filePath.getPath() + "/" + name + ".jpg")));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                String error =  "error," + e.getMessage();
                return FormatResultGenerator.genErrorResult(error);
            } catch (IOException e) {
                e.printStackTrace();
                String error =  "error," + e.getMessage();
                return FormatResultGenerator.genErrorResult(error);
            }
            String result =  "http://" + ip + ":8080/anbbs/upload/avatar/" + name + ".jpg";
            return FormatResultGenerator.genSuccessResult(result);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/block",method = RequestMethod.POST)
    public FormatResult<String> uploadBlockIcon(@RequestParam(value="file")MultipartFile file, HttpServletRequest request) {
        String name = System.currentTimeMillis() + "";
        String path = request.getSession().getServletContext().getRealPath("/upload/block/");
        File filePath = new File(path);
        if(!filePath.exists()) {
            filePath.mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File( filePath.getPath() + "/" + name + ".jpg")));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                String error =  "error," + e.getMessage();
                return FormatResultGenerator.genErrorResult(error);
            } catch (IOException e) {
                e.printStackTrace();
                String error =  "error," + e.getMessage();
                return FormatResultGenerator.genErrorResult(error);
            }
            String result =  "http://" + ip + ":8080/anbbs/upload/block/" + name + ".jpg";
            return FormatResultGenerator.genSuccessResult(result);
        } else {
            return null;
        }
    }

}

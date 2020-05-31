package com.n.controller;

import com.n.dao.IMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private IMediaDao mediaDao;

    @ResponseBody
    @RequestMapping("/findAll.do")
    public ResponseEntity<String> findAll() throws Exception{
        List<String> nameList = mediaDao.findAll();
        String result = "";
        for (String s: nameList) {
            result = result + s + "\n";
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType("text","html", Charset.forName("UTF-8")));
        return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/findLinks.do")
    public ResponseEntity<String> findLinks(String aname) throws Exception{
        String links = mediaDao.findLinksByAname(aname.trim());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType("text","html", Charset.forName("UTF-8")));
        return new ResponseEntity<String>(links, responseHeaders, HttpStatus.OK);
    }
}

package com.sanxia.dove.platform.controller.file;


import com.sanxia.dove.platform.dto.FileStorer;
import com.sanxia.dove.platform.service.FileService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zy on 2018/3/2.
 */
@Controller
@RequestMapping("/Dove")
public class FileController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FileService fileService;
    @Autowired
    HttpServletRequest request;

    /***
     * 通过URL分析出路径，并且得到该路径下所有文件/文件夹
     * @param username 用户名
     * @param nowFloder 需要进入的文件夹名
     * @param model
     * @return 返回到页面并显示
     */
    @RequestMapping("/{username}/Floder/**")
    public String Ergodicfile(String username,String nowFloder,Model model) {
        if (username == "" || username == null) {
            username = "test";
        }
        //获取路径
        StringBuffer requestURL = request.getRequestURL(); //整个URL
        if(requestURL.charAt(requestURL.length()-1) !='/'){
            requestURL.append('/');
        }
        String servletPath = request.getServletPath();    //去掉localhost:8080的部分

        //拼凑resources文件夹路径
        String path = "E:/"+servletPath;
        logger.info(path);
        FileStorer fileStorer = fileService.getFloderDir(path,requestURL.toString());

        model.addAttribute("files",fileStorer);
        return "dove/index";
    }

    /***
     * 显示文件内容
     * @param username 用户名
     * @param model
     * @return 返回页面 显示文件
     */
    @RequestMapping("/{username}/Blob/**")
    public String shouFile(String username,Model model){
        String servletPath = request.getServletPath();    //去掉localhost:8080的部分

        //拼凑resources文件夹路径
        String path = "E:/"+servletPath;
        FileStorer fileStorer = fileService.showFile(path.replaceFirst("Blob","Floder"));
        logger.info(fileStorer.getFileContent());
        model.addAttribute("file",fileStorer);
        return "dove/file";
    }
}

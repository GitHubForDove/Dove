package com.sanxia.dove.platform.service.impl;


import com.sanxia.dove.platform.dto.FileStorer;
import com.sanxia.dove.platform.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zy on 2018/3/2.
 */
@Service
public class FileServiceImpl implements FileService {
    /***
     *
     * @param path 文件夹路径
     * @param URL  URL路径
     * @return
     */
    public FileStorer getFloderDir(String path, String URL) {
        File file = new File(path);

        Map<String,String> FloderNames = new HashMap<String, String>();
        Map<String,String> FileNames = new HashMap<String, String>();

        LinkedList<File> list = new LinkedList<File>();
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                FloderNames.put(file2.getName(),URL);
            } else {
                //文件夹链接改为文件链接
                FileNames.put(file2.getName(),URL.replaceFirst("Floder","Blob"));
            }
        }
        FileStorer fileStorer = new FileStorer(FloderNames,FileNames,"success");
        return fileStorer;
    }

    public FileStorer showFile(String path ) {
        FileStorer fileStorer = new FileStorer();
        String[] pathSplit = path.split("/");
        String filename = pathSplit[pathSplit.length-1];
        String encoding ="UTF-8";
        File file = new File(path);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String result = new String(fileContent, encoding);
            fileStorer = new FileStorer(result,"java","success");

        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
        return fileStorer;
    }

    public String fileType(String fileName) {
        if (fileName == null) {
            fileName = "文件名为空！";
            return fileName;
        } else {
            // 获取文件后缀名并转化为写，用于后续比较
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();

            // 创建图片类型数组
            String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                    "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
            for (int i = 0; i < img.length; i++) {
                if (img[i].equals(fileType)) {
                    return "pic";
                }
            }

            // 创建文档类型数组
            String document[] = { "txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" };
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(fileType)) {
                    return "docx";
                }
            }

            // 创建视频类型数组
            String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
            for (int i = 0; i < video.length; i++) {
                if (video[i].equals(fileType)) {
                    return "视频";
                }
            }

            // 创建音乐类型数组
            String music[] = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                    "m4a", "vqf" };
            for (int i = 0; i < music.length; i++) {
                if (music[i].equals(fileType)) {
                    return "音乐";
                }
            }
        }
        return "其他";
    }

}

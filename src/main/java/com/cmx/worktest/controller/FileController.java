package com.cmx.worktest.controller;


import com.cmx.worktest.FileInfo;
import com.cmx.worktest.manager.FileManager;
import com.cmx.worktest.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/operatorFile")
public class FileController {


    @Autowired
    FileManager fileManager;

    @Value("${file.upload.path.name}")
    private String pathName;

    @RequestMapping("/upload")
    public String upload(Model model){
        model.addAttribute("pathName", pathName);
        return "pages/upload";
    }

    /**
     *
     * @param path 需要传文件到那个目录下面
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(Model model, String path, HttpServletRequest request){

        System.out.println("upload Filr and path : " + path);
        if(null ==path){
            path = "C:\\web\\";
        }

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        for(MultipartFile file : files) {
            if (file.isEmpty()) {
                model.addAttribute("message", "未选择文件!");
                return "error/uploaderror";
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            // 文件上传路径
            String filePath = path;

            // 解决中文问题，liunx下中文路径，图片显示问题
            // fileName = UUID.randomUUID() + suffixName;

            File dest = new File(filePath + fileName);

            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            try {
                file.transferTo(dest);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                model.addAttribute("message", e);
                return "error/uploaderror";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", e);
                return "error/uploaderror";
            }
        }
        return "success/loadsuccess";
    }


    @RequestMapping("/home")
    public String rootPath(Model model){
        List<String> roots = SystemUtil.homeList();
        model.addAttribute("roots", roots);
        return "pages/index";
    }

    @RequestMapping("/showFile")
    public String showFile(Model model, String path){
        if(path == null){
            path = SystemUtil.getHomePath();
        }

        File f = new File(path);
        Map<String, Object> result =  fileManager.getFileSet(f);
        model.addAttribute("files", ((List<FileInfo>)result.get("files")));
        model.addAttribute("directorys", ((List<FileInfo>)result.get("directorys")));
        return "pages/showFile";
    }

    /**
     * 获取路径下所有文件名称
     * @param path
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPath", method = RequestMethod.GET)
    public Map<String, Object> getPathSet(String path){

        File f = new File(path);
        Map<String, Object> result =  fileManager.getFileSet(f);
        return result;
    }

    /**
     * 删除指定文件， 如果是个文件夹， 递归删除
     * 注意： 这里的删除不能回到垃圾箱， 不能恢复
     * @param path
     * @return
     */
    @RequestMapping("/deletePath")
    @ResponseBody
    public String delete(String path){
        File f = new File(path);
        boolean isdelete = fileManager.deleteFile(f);
        if(isdelete){

        }else{
            return "未能删除指定文件";
        }
        return "删除成功";
    }

    /**
     * 下载指定文件 一定是下载 并且名称和原文件的名称相同
     * @param response
     * @param downPath
     */
    @ResponseBody
    @RequestMapping("/downFile")
    public void downLoadFile(HttpServletResponse response, String downPath){
        File f = new File(downPath);
        String fileName;
        if(f.isDirectory()){
            System.out.println("下载的文件是个目录");
            return;
        }else{
            fileName = f.getName();
        }

        try{
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            fileManager.downloadFile(response, f);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
package com.cmx.loader.manager;


import com.cmx.loader.FileInfo;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileManager {

    public boolean deleteFile(File dir){

        if (dir.isDirectory()) {
            String[] children = dir.list();

            for (int i=0; i<children.length; i++) {
                boolean success = deleteFile(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();


    }

    public Map<String, Object> getFileSet(File f){
        Map<String, Object> result = new HashMap<>();
        List<FileInfo> files = new ArrayList<>();
        List<FileInfo> directorys = new ArrayList<>();
        if(f.isFile()){
            FileInfo fileInfo = new FileInfo();
            fileInfo.setDirectory(false);
            fileInfo.setSuffix(f.getName().substring(f.getName().lastIndexOf("."), f.getName().length()));
            fileInfo.setFileName(f.getName());
            fileInfo.setAbsolutePath(f.getAbsolutePath().replace("\\", "/"));
            files.add(fileInfo);
            result.put("files", files);
            return result;
        }

        if(f.isDirectory()){
            File[] fs = f.listFiles();
            for(File file : fs){
                FileInfo fileInfo = new FileInfo();
                if(file.isFile()){
                    fileInfo.setDirectory(false);
                    if(file.getName().lastIndexOf(".")!= -1) {
                        fileInfo.setSuffix(file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()));
                    }
                    fileInfo.setFileName(file.getName());
                    fileInfo.setAbsolutePath(file.getAbsolutePath().replace("\\", "/"));
                    files.add(fileInfo);
                }
                if(file.isDirectory()){
                    fileInfo.setDirectory(true);
                    fileInfo.setFileName(file.getName());
                    fileInfo.setAbsolutePath(file.getAbsolutePath().replace("\\", "/"));
                    fileInfo.setSubCount(file.listFiles()!=null?file.listFiles().length:0);
                    directorys.add(fileInfo);
                }
            }
            result.put("files", files);
            result.put("directorys", directorys);
        }

        return result;
    }

    public void downloadFile(HttpServletResponse response, File file) throws IOException{
        FileInputStream fin;
        fin = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buff = new byte[1024];
        int length;
        while((length = fin.read(buff)) != -1){
            outputStream.write(buff, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        fin.close();
    }

}

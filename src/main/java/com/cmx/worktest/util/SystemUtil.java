package com.cmx.worktest.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取操作系统的相关信息
 */
public class SystemUtil {

    public static String getHomePath(){
        String systemType = System.getProperty("os.name");
        if(systemType.indexOf("Windows") != -1){
            return "c:/";
        }else{
            return "/home/";
        }
    }

    /**
     * 获取windows 下的 盘符 linux 不知道是不是主目录
     * @return
     */
    public static List<String> homeList(){
        List<String> rootList = new ArrayList<>();
        File[] roots = File.listRoots();
        for(File root : roots){
            rootList.add(root.toString().replace("\\", "/"));
        }
        return rootList;
    }


}

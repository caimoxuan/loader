package com.cmx.worktest;

public class FileInfo {

    private String fileName;      //文件名称
    private String suffix;        //后缀名
    private Integer subCount;     //子文件数量
    private String absolutePath; //绝对路径
    private boolean directory;    //是否文件夹

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }


    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
}

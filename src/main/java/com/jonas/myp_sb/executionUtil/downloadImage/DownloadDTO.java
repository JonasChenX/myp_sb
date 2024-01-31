package com.jonas.myp_sb.executionUtil.downloadImage;

public class DownloadDTO {

    private String fileNm;
    private String fileUrl;

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "DownloadDTO{" +
                "fileNm='" + fileNm + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

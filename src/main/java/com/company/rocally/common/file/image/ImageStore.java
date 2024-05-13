package com.company.rocally.common.file.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ImageStore {

    public static String fileDir;

    @Value("${file.dir}")
    public void setFileDir(String value) {
        fileDir = value;
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 확장자를 가져오는 메서드
     */
    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 파일 이름만 가져오는 메서드
     */
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * UUID값을 파일이름에 붙여줘서 가져와주는 메서드
     */
    public static String getFileNameWithUUID(String filename) {
        return UUID.randomUUID().toString() + "_" + filename;
    }


}

package com.company.rocally.common.file.image;

import com.company.rocally.common.dto.ImageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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

    public static File createFile(String uploadPath, String filename) {
        return new File(uploadPath, filename);
    }

    /**
     * 이미지 파일을 uuid값을 포함한 파일명으로 fileDir경로에 저장
     */
    public static File getMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = createFile(fileDir, getFileNameWithUUID(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }

    public static List<ImageDto> getFileDtoFromMultipartFile(List<MultipartFile> multipartFile) throws IOException {
        return multipartFile.stream()
                .map(multipartFile1 -> {
                    try {
                        File file = getMultipartFileToFile(multipartFile1);
                        String fileName = file.getName();
                        String fileType = getExtension(multipartFile1.getOriginalFilename());
                        Long fileSize = multipartFile1.getSize();
                        return new ImageDto(fileName, fileDir + fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                })
                .collect(Collectors.toList());
    }

}

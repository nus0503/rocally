package com.company.rocally.common.validation.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@Component
public class FileUploadValidator implements ConstraintValidator<FileUploadValid, List<MultipartFile>> {

    private FileUploadValid annotation;
    @Override
    public void initialize(FileUploadValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (multipartFiles.isEmpty()) {
            context.buildConstraintViolationWithTemplate("업로드 대상 파일이 없습니다. 정확히 선택 업로드해주세요.")
                    .addConstraintViolation();
            return false;
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String filename = multipartFile.getOriginalFilename();

            if (StringUtils.isBlank(filename)) {
                context.buildConstraintViolationWithTemplate("업로드 요청한 파일명이 존재하지 않습니다.")
                        .addConstraintViolation();
                return false;
            }

            try {
                int targetByte = multipartFile.getBytes().length;
                if (targetByte == 0) {
                    context.buildConstraintViolationWithTemplate("파일의 용량이 0 byte입니다.")
                            .addConstraintViolation();
                    return false;
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                context.buildConstraintViolationWithTemplate("파일의 용량 확인 중 에러가 발생했습니다.")
                        .addConstraintViolation();
                return false;
            }
        }

        // 허용된 파일 확장자 검사
        final String[] detechedMediaType = this.getMimeTypeByTika(multipartFiles);
        UploadAllowFileDefine[] allowExtensionArray = annotation.allowFileDefine();

        int i = 0;

        for (MultipartFile multipartFile : multipartFiles) {
            String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());



            for (UploadAllowFileDefine allowDefine : allowExtensionArray) {
                // 파일명의 허용 확장자 검사
                if (!StringUtils.equals(allowDefine.getFileExtensionLowerCase(), fileExtension.toLowerCase())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("허용되지 않는 확장자의 파일이며 다음 확장자들만 허용됩니다.");
                    sb.append(": ");
                    sb.append(ArrayUtils.toString(allowExtensionArray));
                    context.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();

                    return false;
                }

                if (!ArrayUtils.contains(allowDefine.getAllowMimeTypes(), detechedMediaType[i])) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("확장자 변조 파일은 허용되지 않습니다.");
                    context.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();

                    return false;
                }

            }
            i++;
        }


        return true;
    }

    /**
     * apache Tika 라이브러리를 이용해서 파일의 mimeType을 가져옴
     * @param multipartFiles
     * @return
     */
    private String[] getMimeTypeByTika(List<MultipartFile> multipartFiles) {
        String[] mimeTypes = new String[multipartFiles.size()];
        Tika tika = new Tika();
        try {
            for (int i = 0; i < multipartFiles.size(); i++) {
                MultipartFile multipartFile = multipartFiles.get(i);
                InputStream inputStream = multipartFile.getInputStream();
                String mimeType = tika.detect(inputStream);
                log.debug("업로드 요청된 파일 {}의 mimeType={}", multipartFile.getOriginalFilename(), mimeType);
                inputStream.close();
                mimeTypes[i] = mimeType;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return mimeTypes;
    }
}

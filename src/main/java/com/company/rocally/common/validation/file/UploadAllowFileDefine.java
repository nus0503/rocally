package com.company.rocally.common.validation.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 업로드 허용 파일에 대한 정의 Enum
 */

@Getter
@AllArgsConstructor
public enum UploadAllowFileDefine {

    IMG("png", new String[]{"image/jpg", "image/png"});

    private String fileExtensionLowerCase; // 파일 확장자(소문자)
    private String[] allowMimeTypes; // 허용되는 mime type array(파일 내용 변조 후 확장자를 변경하는 공격을 막기 위해서 사용
}

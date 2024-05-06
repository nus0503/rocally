package com.company.rocally.service.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    public UserImageDto saveFile(UserImageDto dto) {
        log.info("saveFile() saveFile : {}", dto);
        return dto.from(userImageRepository.save(dto.toEntity()));
    }
}

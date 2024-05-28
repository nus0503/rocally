package com.company.rocally.common.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageableRequest {
    private static final int DEFAULT_PAGE_SIZE = 10; // 한 페이지에 존재하는 게시글 수

    private int page = 1;
    private final int pageSize = DEFAULT_PAGE_SIZE;

    public Pageable toPageable() {
        return PageRequest.of(this.page - 1, pageSize, createDateDesc());
    }

    private Sort createDateDesc() {
        return Sort.by(Sort.Direction.DESC, "createdDate");
    }
}

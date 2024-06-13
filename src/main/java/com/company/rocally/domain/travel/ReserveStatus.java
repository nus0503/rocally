package com.company.rocally.domain.travel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReserveStatus {

    COMPLETE,
    WAIT,
    CANCEL
}

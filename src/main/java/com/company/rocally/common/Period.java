package com.company.rocally.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Period {

    public static DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}

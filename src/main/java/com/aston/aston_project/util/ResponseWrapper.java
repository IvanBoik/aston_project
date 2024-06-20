package com.aston.aston_project.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class ResponseWrapper{
    private final int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private final LocalDateTime date = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    private final String timezone = "UTC+3";
    private final Object message;

    public ResponseWrapper(int status, Object message) {
        this.status = status;
        this.message = message;
    }
}

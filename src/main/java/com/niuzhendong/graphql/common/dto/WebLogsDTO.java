package com.niuzhendong.graphql.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WebLogsDTO extends BaseDTO {
    private static final long serialVersionUID = 8654485582322279564L;
    private String logClassName;
    private String logInfo;
    private String logMethodName;
    private String logOperationType;
    private String logPrimaryKey;
    private String logTableName;
    private String logUrl;
    private String logUserName;
    private String module;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime startTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime endTime;
    private String logServerIp;
    private String logClientIp;
    private String info;
    private String type;
    private String r1;
    private String r2;
    private String r3;
    private String r4;
    private String r5;
}

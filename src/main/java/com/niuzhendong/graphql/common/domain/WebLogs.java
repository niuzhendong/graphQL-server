package com.niuzhendong.graphql.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord.MatchType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "web_logs"
)
public class WebLogs extends BaseDomain {
    private static final long serialVersionUID = 1L;
    @Column(
            name = "log_user_name"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logUserName;
    @Column(
            name = "start_time"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    @CommonJpaQueryWord(
            func = MatchType.greaterThanOrEqualTo
    )
    private LocalDateTime startTime;
    @Column(
            name = "end_time"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    @CommonJpaQueryWord(
            func = MatchType.lessThanOrEqualTo
    )
    private LocalDateTime endTime;
    @Column(
            name = "log_info"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logInfo;
    @Column(
            name = "log_operation_type"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logOperationType;
    @Column(
            name = "log_table_name"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logTableName;
    @Column(
            name = "log_primary_key"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logPrimaryKey;
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    @Column(
            name = "log_class_name"
    )
    private String logClassName;
    @Column(
            name = "module"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String module;
    @Column(
            name = "remark"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String remark;
    @Column(
            name = "log_method_name"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logMethodName;
    @Column(
            name = "log_url"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logUrl;
    @Column(
            name = "log_server_ip"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logServerIp;
    @Column(
            name = "log_client_ip"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String logClientIp;
    @Column(
            name = "info"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String info;
    @Column(
            name = "type"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String type;
    @Column(
            name = "r1"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String r1;
    @Column(
            name = "r2"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String r2;
    @Column(
            name = "r3"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String r3;
    @Column(
            name = "r4"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String r4;
    @Column(
            name = "r5"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String r5;

}

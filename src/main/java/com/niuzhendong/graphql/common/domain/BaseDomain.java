package com.niuzhendong.graphql.common.domain;

import com.niuzhendong.graphql.common.service.CommonSecurityService;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord.MatchType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseDomain extends BaseCommonDomain {
    @CreatedBy
    @Column(
            name = "created_by"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    protected String createdBy;
    @CreatedDate
    @Column(
            name = "created_date"
    )
    protected LocalDateTime createdDate;
    @LastModifiedBy
    @Column(
            name = "modified_by"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    protected String modifiedBy;
    @LastModifiedDate
    @Column(
            name = "modified_date"
    )
    protected LocalDateTime modifiedDate;
    @Column(
            name = "remark"
    )
    @CommonJpaQueryWord(
            func = MatchType.like
    )
    private String remark;
    @Column(
            name = "flag"
    )
    @CommonJpaQueryWord(
            func = MatchType.equal
    )
    public Integer flag;

    @PrePersist
    public void createAuditInfo() {
        String loginName = CommonSecurityService.instance.getCurrentLoginName();
        this.setCreatedBy(loginName);
        this.setCreatedDate(LocalDateTime.now());
    }
}

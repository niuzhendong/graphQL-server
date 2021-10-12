package com.niuzhendong.graphql.common.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord.MatchType;

@Data
@MappedSuperclass
public class BaseCommonDomain extends CommonDomain {
    @Id
    @Column(
            name = "id"
    )
    @CommonJpaQueryWord(
            func = MatchType.equal
    )
    private String id;
}

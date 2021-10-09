package com.niuzhendong.graphql.domain;

import cn.com.taiji.common.base.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/5/11
 * @describe
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "login_log")
@Where(clause = "flag=1")
public class LoginLog extends BaseDomain {
    private String username;
    private Integer result;
    private String message;
    private String remoteAddress;
}

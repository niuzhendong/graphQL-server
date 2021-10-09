package com.niuzhendong.graphql.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/5/11
 * @describe
 */
@ApiModel(value = "RoomDTO对象")
@Data
@NoArgsConstructor
public class LoginLogDTO{

    private String username;
    private Integer result;
    private String message;
    private String remoteAddress;
}

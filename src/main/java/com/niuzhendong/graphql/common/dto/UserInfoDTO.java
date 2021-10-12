package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(
        value = "userDto对象",
        description = "用户对象userDto"
)
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;
    @ApiModelProperty("用户排序")
    private Integer userIndex;
    @Email(
            message = "邮件格式不正确"
    )
    @ApiModelProperty("邮件")
    private String email;
    @ApiModelProperty("昵称")
    private String nickname;
    @Range(
            min = 0L,
            max = 1L,
            message = "性别填0或1"
    )
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("博客")
    private String blog;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("头像相对路径或者id")
    private String avatar;
    @ApiModelProperty("身份证")
    private String idNumber;
    @ApiModelProperty("出生日期")
    private String birthday;
    @ApiModelProperty("用户积分")
    private Integer integral;
    private byte[] avatarContent;
    @ApiModelProperty("经验值")
    private Integer experience;
    @ApiModelProperty("余额")
    private BigDecimal balance;
    @ApiModelProperty("用户元数据")
    private String userMetadata;
    private String roleId;
    private String deptId;

    public UserInfoDTO(String id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, Integer flag, String remark, String loginName, String password, String userName, String phoneNum, String state, Integer userIndex, String email, String nickname, String gender, String address, String blog, String tag, String avatar, String idNumber, String birthday, Integer integral) {
        this(id, createdBy, createdDate, modifiedBy, modifiedDate, flag, remark, loginName, userName, phoneNum, state, userIndex, email, nickname, gender, address, blog, tag, avatar, idNumber, birthday, integral);
        this.setPassword(password);
    }

    public UserInfoDTO(String id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, Integer flag, String remark, String loginName, String userName, String phoneNum, String state, Integer userIndex, String email, String nickname, String gender, String address, String blog, String tag, String avatar, String idNumber, String birthday, Integer integral) {
        super.setId(id);
        super.setCreatedBy(createdBy);
        super.setCreatedDate(createdDate);
        super.setModifiedBy(modifiedBy);
        super.setModifiedDate(modifiedDate);
        super.setFlag(flag);
        super.setRemark(remark);
        super.setLoginName(loginName);
        super.setUserName(userName);
        super.setPhoneNum(phoneNum);
        super.setState(state);
        this.userIndex = userIndex;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.address = address;
        this.blog = blog;
        this.tag = tag;
        this.avatar = avatar;
        this.idNumber = idNumber;
        this.birthday = birthday;
        this.integral = integral;
    }
}

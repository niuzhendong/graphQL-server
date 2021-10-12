package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.PageDTO;
import com.niuzhendong.graphql.common.dto.UserInfoDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface IUserInfoService<D extends CommonDomain> extends IBaseService<UserInfoDTO, D> {
    String CacheKey_findIdByLoginName = "findIdByLoginName";
    String CacheKey_findIdByPhoneNum = "findIdByPhoneNum";
    String CacheKey_findIdByEmail = "findIdByEmail";

    String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds);

    @Cacheable(
            value = {"findIdByLoginName"},
            key = "#loginName",
            unless = "#result==null"
    )
    String findIdByLoginName(String loginName);

    @Cacheable(
            value = {"findIdByPhoneNum"},
            key = "#phoneNum",
            unless = "#result==null"
    )
    String findIdByPhoneNum(String phoneNum);

    @Cacheable(
            value = {"findIdByEmail"},
            key = "#email",
            unless = "#result==null"
    )
    String findIdByEmail(String email);

    PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO);

    UserInfoDTO findWithPasswordById(String id);

    UserInfoDTO findByPhoneNum(String phoneNum);

    Boolean checkUserExist(String loginName);
}

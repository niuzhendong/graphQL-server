package com.niuzhendong.graphql.common.service;

import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.MenuDTO;
import com.niuzhendong.graphql.common.dto.MenuRouterDTO;
import com.niuzhendong.graphql.common.dto.MenuRouterDTO.meta;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMenuService<D extends CommonDomain> extends IBaseTreeService<MenuDTO, D> {
    String CacheKey_findIdsByMenuUrl = "findIdsByMenuUrl";
    Logger logger = LoggerFactory.getLogger(IMenuService.class);

    @Cacheable(
            value = {"findIdsByMenuUrl"},
            key = "#menuUrl",
            unless = "#result==null"
    )
    List<String> findIdsByMenuUrl(String menuUrl);

    Map findMenuAllForRouter(String loginName);

    default List<MenuRouterDTO> menuListToMenuRouterList(List<MenuDTO> menuDTOList) {
        List<MenuRouterDTO> menuRouterDTOList = new ArrayList();
        menuDTOList.forEach((menuDTO) -> {
            if (StrUtil.isBlank(menuDTO.getMenuUrl()) && StrUtil.isBlank(menuDTO.getMenuDesc())) {
                logger.info("此菜单信息不在vue前端展示：{}", menuDTO.getId());
            } else {
                MenuRouterDTO menuRouterDTO = new MenuRouterDTO();
                menuRouterDTO.setId(menuDTO.getId());
                menuRouterDTO.setParentId(menuDTO.getParentId());
                menuRouterDTO.setComponent(menuDTO.getMenuUrl());
                menuRouterDTO.setPath(menuDTO.getMenuDesc() == null ? "" : menuDTO.getMenuDesc());
                menuRouterDTO.setIndex(menuDTO.getMenuIndex());
                if (StrUtil.isNotBlank(menuDTO.getMenuName())) {
                    menuRouterDTO.setAlwaysShow(true);
                    meta inner = menuRouterDTO.getMenuRouterDtoMetaInstance(menuDTO.getMenuIndex() + "", new ArrayList(), menuDTO.getMenuName(), menuDTO.getSmallIconPath(), false);
                    menuRouterDTO.setMeta(inner);
                    menuRouterDTO.setHidden(false);
                    menuRouterDTO.setName(menuDTO.getId());
                }

                menuRouterDTOList.add(menuRouterDTO);
            }

        });
        menuRouterDTOList.sort((o1, o2) -> {
            return o1.getIndex() - o2.getIndex();
        });
        return menuRouterDTOList;
    }

    CommonCacheUtil getCommonCacheUtil();

    default void deleteCacheOfFindIdsByMenuUrl() {
        this.getCommonCacheUtil().removeByPattern("findIdsByMenuUrl*");
    }
}

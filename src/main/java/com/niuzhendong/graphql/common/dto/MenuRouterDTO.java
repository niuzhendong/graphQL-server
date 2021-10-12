package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuRouterDTO extends BaseTreeDTO implements Comparable<MenuRouterDTO> {
    private static final long serialVersionUID = 8531051872528629002L;
    @ApiModelProperty("请求路径")
    private String path;
    @ApiModelProperty("组件")
    private String component;
    private Boolean hidden;
    @ApiModelProperty("是否显示")
    private Boolean alwaysShow;
    @ApiModelProperty("路由名称")
    private String name;
    @ApiModelProperty("菜单数据")
    private MenuRouterDTO.meta meta;
    @ApiModelProperty("排序子弹")
    private Integer index;

    public MenuRouterDTO.meta getMenuRouterDtoMetaInstance(String index, List role, String title, String icon, Boolean cacheable) {
        return new MenuRouterDTO.meta(index, role, title, icon, cacheable);
    }

    public int compareTo(MenuRouterDTO o) {
        return this.getIndex() - o.getIndex();
    }

    @Data
    public class meta implements Serializable {
        private static final long serialVersionUID = 8632051832528629005L;
        private String index;
        private List roles;
        private String title;
        private String icon;
        private Boolean cacheAble;

        public meta(String index, List role, String title, String icon, Boolean cacheable) {
            this.setTitle(title);
            this.setRoles(role);
            this.setIndex(index);
            this.setIcon(icon);
            this.setCacheAble(cacheable);
        }
    }
}

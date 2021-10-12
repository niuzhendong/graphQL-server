package com.niuzhendong.graphql.common.service;

import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.BaseTreeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface IBaseTreeService<DTO extends BaseTreeDTO, D extends CommonDomain> extends IBaseService<DTO, D> {
    @Transactional(
            rollbackFor = {Exception.class}
    )
    Boolean move(String currentParentId, String targetParentId, String currentId);

    List<DTO> findByParentId(String parentId);

    List<DTO> findChildrenByParentId(String parentId);

    default <T extends BaseTreeDTO> List<T> listToTree(Collection<T> list, String rootNodeParentId) {
        List<T> treeList = new ArrayList();
        Iterator var4 = list.iterator();

        while(true) {
            while(var4.hasNext()) {
                T node = (T)var4.next();
                if (StrUtil.isBlank(rootNodeParentId) && StrUtil.isBlank(node.getParentId())) {
                    treeList.add(this.findChildren(node, list));
                } else if (node.getParentId().equals(rootNodeParentId)) {
                    treeList.add(this.findChildren(node, list));
                }
            }

            return treeList;
        }
    }

    default <T extends BaseTreeDTO> T findChildren(T tree, Collection<T> list) {
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            T node = (T)var3.next();
            if (tree.getId().equals(node.getParentId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList());
                }

                tree.getChildren().add(this.findChildren(node, list));
            }
        }

        return tree;
    }

    default String createPath(DTO dto) {
        String parentId = dto.getParentId();
        if (StrUtil.isBlank(parentId)) {
            return dto.getId();
        } else {
            DTO parent = (DTO)this.findById(parentId);
            String parentPath = parent.getPath();
            return parentPath + "," + dto.getId();
        }
    }
}


package cn.pzh.system.provider.service.impl;

import cn.pzh.common.config.model.MenuNode;
import cn.pzh.system.provider.dao.first.mapper.MenuMapper;
import cn.pzh.system.provider.dao.first.model.SystemMenuEntity;
import cn.pzh.system.provider.service.MenuService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuNode> getMenuList(String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("parentMenuCode", "0");
        List<SystemMenuEntity> parentMenus = menuMapper.getMenu(map);
        List<MenuNode> menuNodeList = new ArrayList<>();
        parentMenus.forEach(item -> {
            MenuNode menuNode = new MenuNode();
            map.put("parentMenuCode", item.getMenuCode());
            menuNode.setParent(item);
            menuNode.setChildren(menuMapper.getMenu(map));
            menuNodeList.add(menuNode);
        });
        return menuNodeList;
    }
}

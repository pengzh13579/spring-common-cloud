package cn.pzh.system.provider.service;

import cn.pzh.common.config.model.MenuNode;
import java.util.List;

public interface MenuService {
    List<MenuNode> getMenuList(String userName);
}

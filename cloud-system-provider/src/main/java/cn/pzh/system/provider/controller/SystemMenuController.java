package cn.pzh.system.provider.controller;

import cn.pzh.common.config.model.MenuNode;
import cn.pzh.system.provider.service.MenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemMenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping ("/getMenuList")
    public List<MenuNode> getMenuList(String userName) {
        return menuService.getMenuList(userName);
    }
}

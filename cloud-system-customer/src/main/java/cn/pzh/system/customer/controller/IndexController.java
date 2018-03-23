package cn.pzh.system.customer.controller;

import cn.pzh.system.customer.feign.MenuFeignHystrixClient;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/indexController")
public class IndexController {

    @Autowired
    private MenuFeignHystrixClient menuFeignHystrixClient;

    /**
     * 首页
     *
     * @return 首页
     */
    @RequestMapping ("/index")
    public String userPage(HttpServletRequest request) {
        request.setAttribute("userName", SecurityUtils.getSubject().getPrincipal().toString());
        request.setAttribute("parentMenu", this.menuFeignHystrixClient.
                getMenuList(SecurityUtils.getSubject().getPrincipal().toString()));
        return "index";
    }

}

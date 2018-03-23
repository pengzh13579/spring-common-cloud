package cn.pzh.system.customer.feign;

import cn.pzh.common.config.model.MenuNode;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用@FeignClient注解的fallback属性，指定fallback类
 *
 * @author eacdy
 */
@FeignClient (name = "cloud-system-provider")
public interface MenuFeignHystrixClient {

    @RequestMapping ("/getMenuList")
    public List<MenuNode> getMenuList(@RequestParam ("userName") String userName);

}

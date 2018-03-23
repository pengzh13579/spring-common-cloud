package cn.pzh.system.customer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication (exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableFeignClients
@EnableDiscoveryClient
public class SystemCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemCustomerApplication.class, args);
    }

    /**
     * 登录
     *
     * @return 登录
     */
    @RequestMapping ("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录
     *
     * @return 登录
     */
    @RequestMapping ("/loginPage")
    public String loginPage() {
        return "login";
    }

    /**
     * 退出
     *
     * @return 退出
     */
    @RequestMapping ("/logout")
    public String logout() {
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        // 修改用户表在线状态
        //userService.updateOnlineStatus(userName);
        return "login";
    }
}

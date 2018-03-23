package cn.pzh.system.customer.feign;

import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
public interface UserFeignHystrixClient {

    @RequestMapping ("/getAll")
    public List<SystemUserEntity> getAll();

    @RequestMapping ("/registration")
    public Boolean registration(@RequestParam ("userEntity") SystemUserEntity userEntity)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @RequestMapping ("/loginCheck")
    public String loginCheck(@RequestParam ("userName") String userName, @RequestParam ("password") String password,
            @RequestParam ("rememberFlag") boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @RequestMapping ("/getUserNameByName")
    public SystemUserEntity getUserNameByName(@RequestParam ("userName") String userName);
}

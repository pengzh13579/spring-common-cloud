package cn.pzh.system.provider.controller;

import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import cn.pzh.system.provider.service.UserService;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemUserController {

    @Autowired
    private UserService userService;

    @RequestMapping ("/getAll")
    public List<SystemUserEntity> getAll() {
        return userService.getAll();
    }

    @RequestMapping ("/registration")
    public Boolean registration(SystemUserEntity userEntity)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.registration(userEntity);
    }

    @RequestMapping ("/getUserNameByName")
    public SystemUserEntity getUserNameByName(String userName) {
        return userService.getUserNameByName(userName);
    }
}

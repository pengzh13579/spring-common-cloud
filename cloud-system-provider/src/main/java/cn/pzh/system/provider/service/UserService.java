package cn.pzh.system.provider.service;

import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    List<SystemUserEntity> getAll();

    Boolean registration(SystemUserEntity userEntity) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    void updateOnlineStatus(String userName);

    SystemUserEntity getUserNameByName(String userName);
}

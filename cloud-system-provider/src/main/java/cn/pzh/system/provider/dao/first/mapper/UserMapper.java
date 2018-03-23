package cn.pzh.system.provider.dao.first.mapper;

import cn.pzh.system.provider.dao.first.model.SystemContactEntity;
import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import java.util.List;

public interface UserMapper {
    List<SystemUserEntity> getAll();

    SystemUserEntity getUserNameByName(String userName);

    void saveUser(SystemUserEntity userEntity);

    void saveContact(SystemContactEntity contactEntity);
}

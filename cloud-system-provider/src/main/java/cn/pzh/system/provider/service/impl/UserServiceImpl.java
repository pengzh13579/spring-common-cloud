package cn.pzh.system.provider.service.impl;

import cn.pzh.common.config.constant.WebConstants;
import cn.pzh.common.config.utils.IdUtils;
import cn.pzh.common.config.utils.MD5Util;
import cn.pzh.system.provider.dao.first.mapper.UserMapper;
import cn.pzh.system.provider.dao.first.model.SystemContactEntity;
import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import cn.pzh.system.provider.service.UserService;
import cn.pzh.system.provider.utils.CommonFieldUtils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service ("userService")
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<SystemUserEntity> getAll() {
        return userMapper.getAll();
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean registration(SystemUserEntity userEntity)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //用户信息
        userEntity.setSalt(IdUtils.salt(WebConstants.SALT_SIZE));
        userEntity.setPassword(MD5Util.EncoderStringByMd5(userEntity.getPassword() + userEntity.getSalt()));
        CommonFieldUtils.setAdminCommon(userEntity, "registration");
        userMapper.saveUser(userEntity);

        //联系方式，注册只有邮箱
        SystemContactEntity systemContactEntity = new SystemContactEntity();
        systemContactEntity.setContact(userEntity.getEmail());
        systemContactEntity.setUserId(userEntity.getIId());
        systemContactEntity.setTypeDetailId(new Long(1));
        CommonFieldUtils.setAdminCommon(systemContactEntity, "registration");

        userMapper.saveContact(systemContactEntity);

        return true;
    }

    @Override
    public void updateOnlineStatus(String userName) {

    }

    @Override
    public SystemUserEntity getUserNameByName(String userName) {
        return userMapper.getUserNameByName(userName);
    }
}

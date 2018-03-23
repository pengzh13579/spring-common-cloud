package cn.pzh.system.provider.utils;

import cn.pzh.system.provider.dao.first.model.CommonEntity;
import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import java.util.Date;

public class CommonFieldUtils {

    /**
     * 共通字段处理
     */
    public static void setAdminCommon(CommonEntity entity, String user) {

        //系统日期
        Date systemDate = new Date();

        entity.setDisFlag(0);
        entity.setCreateUser(user);
        entity.setUpdateUser(user);
        entity.setCreateDate(systemDate);
        entity.setUpdateDate(systemDate);
    }

    /**
     * 共通字段处理
     */
    public static void setAdminCommon(SystemUserEntity entity, boolean flag) {

        //系统日期
        Date systemDate = new Date();
        entity.setDisFlag(0);
        entity.setUpdateUser("admin");
        entity.setUpdateDate(systemDate);
        if (flag) {
            entity.setCreateUser("admin");
            entity.setCreateDate(systemDate);
        }
    }
}

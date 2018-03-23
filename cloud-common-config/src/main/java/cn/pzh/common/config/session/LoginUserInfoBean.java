package cn.pzh.common.config.session;

import java.util.Date;
import lombok.Data;

/**
 * @author pengzh
 * @date 2016/07/20
 */
@Data
public class LoginUserInfoBean implements java.io.Serializable {

    private String userName;
    private String realName;
    private String password;
    private String email;
    private Date loginTime;
}

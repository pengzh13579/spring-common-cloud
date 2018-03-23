package cn.pzh.system.provider.dao.first.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserEntity extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    private Long iId;
    private String userName;
    private String realName;
    private String password;
    private String salt;
    private int isOnline;
    private String email;

}

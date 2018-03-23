package cn.pzh.system.provider.dao.first.model;

import java.util.Date;
import lombok.Data;

@Data
public class CommonEntity {

    private int disFlag;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
}

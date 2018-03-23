package cn.pzh.system.provider.dao.first.model;

import lombok.Data;

@Data
public class SystemContactEntity extends CommonEntity {

    private Long iId;
    private Long userId;
    private Long typeDetailId;
    private String contact;

}

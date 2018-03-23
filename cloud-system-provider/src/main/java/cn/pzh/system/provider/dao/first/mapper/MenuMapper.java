package cn.pzh.system.provider.dao.first.mapper;

import cn.pzh.system.provider.dao.first.model.SystemMenuEntity;
import java.util.List;
import java.util.Map;

public interface MenuMapper {

    List<SystemMenuEntity> getMenu(Map<String, Object> map);

}

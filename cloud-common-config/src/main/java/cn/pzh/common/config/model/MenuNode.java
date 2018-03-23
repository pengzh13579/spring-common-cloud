package cn.pzh.common.config.model;

import java.util.List;
import lombok.Data;

@Data
public class MenuNode<T> {
    private T parent;
    private List<T> children;
}

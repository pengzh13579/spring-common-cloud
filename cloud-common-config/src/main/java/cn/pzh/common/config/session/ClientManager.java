package cn.pzh.common.config.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengzh
 * @date 2016/07/20
 */
public class ClientManager {

    //创建对象，使用单例模式
    private static ClientManager instance = new ClientManager();
    //存储session及登录用户的Bean
    private Map<String, LoginUserInfoBean> map = new HashMap<String, LoginUserInfoBean>();

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        return instance;
    }

    /**
     * 添加登录用户
     */
    public void addClient(String sessionId, LoginUserInfoBean loginUserInfoBean) {
        map.put(sessionId, loginUserInfoBean);
    }

    /**
     * 移除登录用户
     */
    public void removeClient(String sessionId) {
        map.remove(sessionId);
    }

    /**
     * 获取登录用户
     */
    public LoginUserInfoBean getClient() {
        return map.get(SessionManager.getSession().getId());
    }

    /**
     * 获取所有登录用户
     */
    public Collection<LoginUserInfoBean> getAllClient() {
        return map.values();
    }

}

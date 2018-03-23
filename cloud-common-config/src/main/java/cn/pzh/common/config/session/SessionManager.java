package cn.pzh.common.config.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author pengzh
 * @date 2016/07/20
 */
public class SessionManager {

    /**
     * 获取request对象
     *
     * @return request对象
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 获取session对象
     *
     * @return session对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }
}

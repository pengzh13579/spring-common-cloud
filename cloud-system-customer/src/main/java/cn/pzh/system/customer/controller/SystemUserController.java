package cn.pzh.system.customer.controller;

import cn.pzh.common.config.constant.MessageConstants;
import cn.pzh.common.config.constant.WebConstants;
import cn.pzh.common.config.model.AjaxJson;
import cn.pzh.common.config.model.PageInfo;
import cn.pzh.common.config.session.LoginUserInfoBean;
import cn.pzh.common.config.utils.MD5Util;
import cn.pzh.system.customer.feign.UserFeignHystrixClient;
import cn.pzh.system.provider.dao.first.model.SystemUserEntity;
import com.github.pagehelper.PageHelper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/systemUserController")
public class SystemUserController {

    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @RequestMapping ("/getUsers")
    public List<SystemUserEntity> getUsers() {
        // 默认从第一页开始，每页五条
        PageHelper.startPage(1, 5);
        List<SystemUserEntity> users = this.userFeignHystrixClient.getAll();
        // 将users对象绑定到pageInfo
        PageInfo<SystemUserEntity> pageUser = new PageInfo<SystemUserEntity>(users);

        return users;
    }

    @RequestMapping ("/addUser")
    public List<SystemUserEntity> addUser(SystemUserEntity userEntity, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean flag = this.userFeignHystrixClient.registration(userEntity);
        return null;
    }

    @RequestMapping ("/loginCheck")
    public AjaxJson loginCheck(String userName, String password, String rememberFlag, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AjaxJson j = new AjaxJson();
        String flag = checkUser(userName, password, "on".equals(rememberFlag) ? true : false);
        if (flag.equals(WebConstants.LOGIN_SUCCESS)) {
            j.setMsg(MessageConstants.LOGIN_SUCCESS_MSG);
            j.setSuccess(true);
        } else {
            j.setMsg(flag.equals(WebConstants.IS_ONLINE)
                    ? MessageConstants.USER_IS_ONLINE_MSG
                    : MessageConstants.LOGIN_ERROR_MSG);
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 将Cookie添加进Session
     */
    private SystemUserEntity setSession(String userName) {
        SystemUserEntity user = this.userFeignHystrixClient.getUserNameByName(userName);
        Session session = SecurityUtils.getSubject().getSession();
        LoginUserInfoBean loginUserInfoBean = new LoginUserInfoBean();
        loginUserInfoBean.setUserName(user.getUserName());
        loginUserInfoBean.setRealName(user.getRealName());
        loginUserInfoBean.setEmail(user.getEmail());
        loginUserInfoBean.setLoginTime(new Date());
        session.setAttribute("userInfo", loginUserInfoBean);
        return user;
    }

    public String checkUser(String userName, String password, boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        SystemUserEntity user = setSession(userName);
        if (null != user) {
            String loginPass = MD5Util.EncoderStringByMd5(password + user.getSalt());
            if (loginPass.equals(user.getPassword())) {
                if (1 != user.getIsOnline()) {
                    //获取当前登陆者
                    Subject userSub = SecurityUtils.getSubject();

                    //创建令牌
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), loginPass);

                    //前台记住我checkbox是否打勾
                    token.setRememberMe(rememberFlag);

                    //登录验证
                    userSub.login(token);
                    user.setIsOnline(1);
                    //user.setLoginTime(new Date());
                    //userMapper.update(user);
                    return WebConstants.LOGIN_SUCCESS;
                }
                return WebConstants.IS_ONLINE;
            }
        }
        return WebConstants.LOGIN_ERROR;
    }
}

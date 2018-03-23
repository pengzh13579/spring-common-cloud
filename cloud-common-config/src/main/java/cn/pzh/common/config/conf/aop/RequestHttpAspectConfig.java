package cn.pzh.common.config.conf.aop;

import cn.pzh.common.config.utils.IpUtil;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequestHttpAspectConfig {

    private final static Logger logger = LoggerFactory.getLogger(RequestHttpAspectConfig.class);

    /**
     * 定义AOP扫描路径 1）execution(* *(..)) 表示匹配所有方法 2）execution(public * com.pzh.system.web.project.sys.controller.SystemUserController.*(..))
     * 表示匹配com.pzh.system.web.project.sys.controller.SystemUserController中所有的公有方法 3）execution(*
     * com.pzh.system.web.project.sys.controller.*.*(..)) 表示匹配com.pzh.system.web.project.sys.controller包及其子包下的所有方法
     */
    @Pointcut ("execution(public * cn.pzh.system.web.project.*.controller.*.*(..))")
    public void log() {
    }

    /**
     * 记录HTTP请求开始时的日志
     */
    @Before ("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //URL
        logger.info("url={}", request.getRequestURI());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("ip={}", IpUtil.getIpAddr(request));
        //类方法
        logger.info("class={} and method name = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        //参数
        logger.info("参数={}", joinPoint.getArgs());
    }

    /**
     * 记录HTTP请求结束时的日志
     */
    @After ("log()")
    public void doAfter() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url = {} end of execution", request.getRequestURL());
    }

    /**
     * 获取返回内容
     */
    @AfterReturning (returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object) {
        logger.info("response={}", object.toString());
    }
}

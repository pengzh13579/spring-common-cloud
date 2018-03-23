package cn.pzh.common.config.conf.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置redis数据源 Java Lib For OIS, Powered By xiaour.
 *
 * @author Zhang.Tao
 * @version V2.0.0
 * @ClassName RedisConfig
 * @Date 2017年4月24日 下午5:25:30
 */
@Configuration
public class RedisConfig {
    private static Logger log = LoggerFactory.getLogger(RedisConfig.class);

    @Bean (name = "spring.redis")
    @Autowired
    public JedisPool jedisPool(@Qualifier ("spring.redis.config") JedisPoolConfig config,
            @Value ("${spring.redis.host}") String host, @Value ("${spring.redis.port}") int port) {
        return new JedisPool(config, host, port);
    }

    @Bean (name = "spring.redis.config")
    public JedisPoolConfig jedisPoolConfig(@Value ("${spring.redis.config.maxTotal}") int maxTotal,
            @Value ("${spring.redis.config.maxIdle}") int maxIdle,
            @Value ("${spring.redis.config.maxWaitMillis}") int maxWaitMillis) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWaitMillis);
            return config;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

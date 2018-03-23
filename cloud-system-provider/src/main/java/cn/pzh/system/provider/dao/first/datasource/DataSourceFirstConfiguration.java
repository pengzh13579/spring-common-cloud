package cn.pzh.system.provider.dao.first.datasource;

import cn.pzh.common.config.constant.WebConstants;
import cn.pzh.system.provider.dao.first.annotation.DatasourceFirstMapper;
import com.github.pagehelper.PageHelper;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan (basePackages = "cn.pzh.system.web.project", annotationClass = DatasourceFirstMapper.class, sqlSessionTemplateRef = WebConstants.FIRST_SQL_SESSION_ID)
public class DataSourceFirstConfiguration {

    @Bean (name = WebConstants.FIRST_DATASOURCE_ID)
    @ConfigurationProperties (prefix = "app.first.datasource")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean (name = WebConstants.FIRST_SQL_SESSION_FACTORY_ID)
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier (WebConstants.FIRST_DATASOURCE_ID) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Interceptor[] plugins = new Interceptor[] { firstPageHelper() };
        bean.setPlugins(plugins);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/cn/pzh/system/web/project/**/dao/first/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean (name = WebConstants.FIRST_DATASOURCE_MBEAN)

    public DataSourceTransactionManager testTransactionManager(
            @Qualifier (WebConstants.FIRST_DATASOURCE_ID) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean (name = WebConstants.FIRST_SQL_SESSION_ID)
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier (WebConstants.FIRST_SQL_SESSION_FACTORY_ID) SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PageHelper firstPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}

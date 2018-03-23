package cn.pzh.system.provider.dao.second.datasource;

import cn.pzh.common.config.constant.WebConstants;
import cn.pzh.system.provider.dao.second.annotation.DatasourceSecondMapper;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan (basePackages = "cn.pzh.system.web.project", annotationClass = DatasourceSecondMapper.class, sqlSessionTemplateRef = WebConstants.SECOND_SQL_SESSION_ID)
public class DataSourceSecondConfiguration {

    @Bean (name = WebConstants.SECOND_DATASOURCE_ID)
    @ConfigurationProperties (prefix = "app.second.datasource")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean (name = WebConstants.SECOND_SQL_SESSION_FACTORY_ID)
    public SqlSessionFactory testSqlSessionFactory(@Qualifier (WebConstants.SECOND_DATASOURCE_ID) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/cn/pzh/system/web/project/**/dao/second/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean (name = WebConstants.SECOND_DATASOURCE_MBEAN)
    public DataSourceTransactionManager testTransactionManager(
            @Qualifier (WebConstants.SECOND_DATASOURCE_ID) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean (name = WebConstants.SECOND_SQL_SESSION_ID)
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier (WebConstants.SECOND_SQL_SESSION_FACTORY_ID) SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

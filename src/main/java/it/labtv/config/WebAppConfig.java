package it.labtv.config;

import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import it.labtv.service.OneTimeService;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "it.labtv.controller")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "it.labtv.repository",
        entityManagerFactoryRef = "emf",
        transactionManagerRef = "tmf")
public class WebAppConfig implements WebMvcConfigurer {

//  @Bean
//  public ViewResolver appResolver() {
//      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/view/");
//		resolver.setSuffix(".jsp");
//		resolver.setViewClass(JstlView.class);
//		return resolver;
//  }
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//      registry.addResourceHandler("assets/**").addResourceLocations("/WEB-INF/view/resources/");
//      registry.addResourceHandler("styles/**").addResourceLocations("/WEB-INF/view/styles/");
//  }

    @Bean
    public DataSource getDbConn() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/labtv?serverTimezone=CET");
//      ds.setUrl("jdbc:mysql://localhost:3306/test_labtv?serverTimezone=CET");
        ds.setUsername("root");
//		ds.setPassword("root");
        return ds;
    }

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean getEMF() {
        HibernateJpaVendorAdapter hJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hJpaVendorAdapter.setDatabase(Database.MYSQL);
        hJpaVendorAdapter.setShowSql(false);
        /// IMPORTANT ///
        hJpaVendorAdapter.setGenerateDdl(true);
        /// IMPORTANT ///
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(this.getDbConn());
        factoryBean.setJpaVendorAdapter(hJpaVendorAdapter);
        factoryBean.setPackagesToScan("it.labtv");
        return factoryBean;
    }

    @Bean(name = "tmf")
    public PlatformTransactionManager getTMF() {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(this.getEMF().getObject());
        return jtm;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(hibernate5Module);
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
        return converter;
    }

    @Bean
    public OneTimeService getOneTimeService() {
        return new OneTimeService();
    }

}

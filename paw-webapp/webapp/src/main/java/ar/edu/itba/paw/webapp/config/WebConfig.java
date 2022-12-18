package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//@EnableWebMvc
@ComponentScan({"ar.edu.itba.paw.webapp.auth","ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.service", "ar.edu.itba.paw.persistence"})
@Configuration
@EnableAsync
@PropertySource(value = {"classpath:application.properties"})
@EnableTransactionManagement
@EnableScheduling

public class WebConfig extends WebMvcConfigurerAdapter {

    private static final boolean DEV_BUILD = false; // Change this to a config/profile in the future

    private static boolean isOnDevBuild() {
        return DEV_BUILD;
    }

//    @Bean
//    public ViewResolver viewResolver() {
//        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//
//        return viewResolver;
//    }

    @Autowired
    private Environment environment;


    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);

        if (isOnDevBuild()) {
            ds.setUrl(environment.getRequiredProperty("db.dev.url"));
            ds.setUsername(environment.getRequiredProperty("db.dev.username"));
            ds.setPassword(environment.getRequiredProperty("db.dev.password"));
        } else {
            ds.setUrl(environment.getRequiredProperty("db.prod.url"));
            ds.setUsername(environment.getRequiredProperty("db.prod.username"));
            ds.setPassword(environment.getRequiredProperty("db.prod.password"));
        }
        return ds;
    }

    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(environment.getRequiredProperty("mail.username"), environment.getRequiredProperty("mail.password"));
                    }
                });
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Autowired AuthenticationFilter authenticationFilter;

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.persistence", "ar.edu.itba.paw");
        factoryBean.setDataSource(dataSource());
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        if (isOnDevBuild()) properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*");
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:8080");
//            }
//        };
//    }
}

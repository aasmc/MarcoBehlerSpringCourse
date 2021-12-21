package ru.aasmc.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import ru.aasmc.ApplicationLauncher;

import javax.sql.DataSource;

/**
 * Spring needs a configuration class in order to construct an [ApplicationContext].
 *
 * @Bean annotation tells Spring that on ApplicationContext startup it should create
 * one instance of the class, e.g. [UserService]. Basically, this will return a singleton.
 * <p>
 * This type of configuration is called Spring's explicit Java configuration.
 * @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) ensures that Spring
 * returns a completely new object every time the object is requested.
 * @ComponentScan tells Spring to scan the classpath and find all @Components in the
 * project. To allow Spring to scan the entire project and all its subdirectories
 * we need to provide basePackageClasses attribute, in our case we can specify ApplicationLauncher.class
 * @EnableWbMvc ensures that Spring Web MVC gets initialized with a default configuration.
 * If we have a jackson dependency, the default configuration automatically registers a JSON
 * converter. And Spring assumes that user wants by default to receive JSON objects and not XML
 * objects or plain text. If we have both JSON and XML converters then they are resolved according
 * to their priority. XML has higher default priority than JSON.
 */
@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties",
        ignoreResourceNotFound = true)
@EnableWebMvc
public class ApplicationConfiguration {

    /**
     * [ThymeleafViewResolver] is used to find and render html templates, that are
     * mentioned in @Controller classes.
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1); // optional
        viewResolver.setViewNames(new String[]{"*.html", "*.xhtml"}); // optional
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    /**
     * [SpringResourceTemplateResolver] actually finds the Thymelieaf templates.
     *
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:~/myFirstH2Database;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
























package ru.aasmc.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.aasmc.ApplicationLauncher;

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
 */
@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties",
        ignoreResourceNotFound = true)
public class PdfInvoiceApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

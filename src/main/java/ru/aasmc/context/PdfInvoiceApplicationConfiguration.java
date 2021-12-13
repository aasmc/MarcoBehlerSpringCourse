package ru.aasmc.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aasmc.service.InvoiceService;
import ru.aasmc.service.UserService;

/**
 * Spring needs a configuration class in order to construct an [ApplicationContext].
 *
 * @Bean annotation tells Spring that on ApplicationContext startup it should create
 * one instance of the class, e.g. [UserService].
 *
 * This type of configuration is called Spring's explicit Java configuration.
 */
@Configuration
public class PdfInvoiceApplicationConfiguration {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public InvoiceService invoiceService(UserService userService) {
        return new InvoiceService(userService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

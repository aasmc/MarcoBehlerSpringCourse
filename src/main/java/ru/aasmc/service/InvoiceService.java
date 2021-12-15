package ru.aasmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aasmc.model.Invoice;
import ru.aasmc.model.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    private final UserService userService;
    private final String cdnUrl;

    private List<Invoice> invoices = new CopyOnWriteArrayList<>();

    /**
     * Annotation @Value lets us inject properties that exist in any of the
     * specified @PropertySource into any Spring bean. The key must be
     * surrounded by ${}. If the property doesn't exist Sprint will throw an
     * exception by default.
     * @param userService
     * @param cdnUrl
     */
    public InvoiceService(
            UserService userService,
            @Value(("${cdn.url}")) String cdnUrl
    ) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
    }

    /**
     * Methods annotated @PostConstruct need to be public.
     */
    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
    }

    @PreDestroy
    public void shutDown() {
        System.out.println("Deleting downloaded templates...");
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalStateException();
        }

        // TODO real pdf creation and storing it on network server
        Invoice invoice = new Invoice(userId, amount, cdnUrl + "images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }
}
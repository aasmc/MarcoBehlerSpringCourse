package ru.aasmc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.aasmc.model.Invoice;
import ru.aasmc.model.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Component
public class InvoiceService {

    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    private final String cdnUrl;

    /**
     * Annotation @Value lets us inject properties that exist in any of the
     * specified @PropertySource into any Spring bean. The key must be
     * surrounded by ${}. If the property doesn't exist Sprint will throw an
     * exception by default.
     *
     * @param userService
     * @param cdnUrl
     */
    public InvoiceService(
            UserService userService,
            JdbcTemplate jdbcTemplate,
            @Value(("${cdn.url}")) String cdnUrl
    ) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
        this.jdbcTemplate = jdbcTemplate;
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
        return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));

            return invoice;
        });
    }

    public Invoice create(String userId, Integer amount) {
        String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into invoices (user_id, pdf_url, amount) values ( ?,?,? )",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            ps.setString(2, generatedPdfUrl);
            ;
            ps.setInt(3, amount);
            return ps;
        }, keyHolder);

        String uuid = !keyHolder.getKeys().isEmpty()
                ? ((UUID) keyHolder.getKeys().values().iterator().next()).toString()
                : null;
        Invoice invoice = new Invoice();
        invoice.setId(uuid);
        invoice.setPdfUrl(generatedPdfUrl);
        invoice.setAmount(amount);
        invoice.setUserId(userId);
        return invoice;
    }
}



























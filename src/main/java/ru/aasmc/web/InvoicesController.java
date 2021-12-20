package ru.aasmc.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aasmc.model.Invoice;
import ru.aasmc.service.InvoiceService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Annotation @Controller tell Spring's component scan, that
 * this class can accept HTTP requests.
 * <p>
 * Annotation @RestController is a meta annotation, and it
 * includes @Controller and @ResponseBody
 *
 * @ResponseBody tells Spring that we intend to write JSON or XML or plain text
 * directly to the HttpServletOutputstream but without going through an HTML templating framework -
 * which Spring would assume by default if we didn't annotate our method with @ResponseBody.
 * If the annotations are put to the class then they are applied to every public method in that class.
 */
@RestController
@Validated
public class InvoicesController {

    private final InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Annotation @GetMapping("/invoices") makes sure that this method is called By Spring MVC
     * in response to HTTP GET request if and only if the request URI is equal to /invoices.
     *
     * @GetMapping is a shorthand for @RequestMapping(value = "/invoices", method = RequestMethod.GET)
     */
    @GetMapping("/invoices")
//    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(
            @RequestParam("user_id") @NotBlank String userId,
            @RequestParam @Min(10) @Max(50) Integer amount
    ) {
        return invoiceService.create(userId, amount);
    }
}


























package ru.aasmc.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aasmc.service.InvoiceService;
import ru.aasmc.service.UserService;

public class Application {
    public static final UserService userService = new UserService();
    public static final InvoiceService invoiceService = new InvoiceService(userService);
    public static final ObjectMapper objectMapper = new ObjectMapper();
}

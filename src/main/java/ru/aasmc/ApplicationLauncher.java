package ru.aasmc;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.aasmc.context.PdfInvoiceApplicationConfiguration;

import javax.servlet.ServletContext;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", null);
        WebApplicationContext appCtx = createApplicationContext(ctx.getServletContext());
        // this servlet will accept all incoming HTTP requests.
        // it needs WebApplicationContext and needs to know about @Controllers
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
        Wrapper servlet = Tomcat.addServlet(ctx, "pdfServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    /**
     * Creates Springs web application context.
     * @param servletContext
     * @return
     */
    private static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(PdfInvoiceApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        // this starts the application context
        ctx.refresh();
        // don't forget to hook the shutdown process.
        ctx.registerShutdownHook();
        return ctx;
    }
}

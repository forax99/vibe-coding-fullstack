package com.example.vibeapp.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servlet =
            servletContext.addServlet("H2Console", new JakartaWebServlet());
        servlet.addMapping("/h2-console/*");
        servlet.setInitParameter("webAllowOthers", "true");
        servlet.setLoadOnStartup(1);
    }
}

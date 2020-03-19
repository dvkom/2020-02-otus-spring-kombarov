package ru.dvkombarov.app;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.dvkombarov.app.service.business.TestingRunner;

import java.io.InputStream;
import java.io.PrintStream;

@ComponentScan
@PropertySource("classpath:application.properties")
@Configuration
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        TestingRunner testingRunner = context.getBean(TestingRunner.class);
        testingRunner.run();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public InputStream inputStream() {
        return System.in;
    }

    @Bean
    public PrintStream printStream() {
        return System.out;
    }
}

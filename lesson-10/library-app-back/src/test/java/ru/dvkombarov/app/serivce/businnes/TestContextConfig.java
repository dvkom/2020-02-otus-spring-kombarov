package ru.dvkombarov.app.serivce.businnes;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.dvkombarov.app.serivce.businnes"})
class TestContextConfig {
}

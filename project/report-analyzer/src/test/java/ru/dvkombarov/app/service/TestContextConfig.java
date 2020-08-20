package ru.dvkombarov.app.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan(value = {"ru.dvkombarov.app.service"}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VulnersService.class)})
class TestContextConfig {
}

package ru.dvkombarov.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.service.infrastructure.InputService;
import ru.dvkombarov.app.service.infrastructure.OutputService;

@SpringBootTest
@DisplayName("Класс Main")
class MainTests {

	@MockBean
	InputService inputService;

	@Test
	@DisplayName("Проверка поднятия контекста")
	void contextLoads() {
	}
}

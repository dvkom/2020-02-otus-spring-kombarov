package ru.dvkombarov.app.service.infrastructure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@DisplayName("Класс LocalizationServiceSimpleImpl")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalizationServiceSimpleImplTest {

    @InjectMocks
    private LocalizationServiceSimpleImpl localizationService;

    @Mock
    private MessageSource messageSource;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("должен вернуть строку, если сообщение найдено")
    @Test
    void shouldReturnStringWhenMessageFound() {
        String message = "message";
        doReturn(message).when(messageSource)
                .getMessage(anyString(), anyObject(), any(Locale.class));
        assertThat(localizationService.getLocalValue("1")).isEqualTo(message);
        assertThat(localizationService.getLocalValue("1", List.of("1"))).isEqualTo(message);
    }

    @DisplayName("должен вернуть null, если сообщение не найдено")
    @Test
    void shouldReturnStringWhenMessageNotFound() {
        doThrow(NoSuchMessageException.class).when(messageSource)
                .getMessage(anyString(), anyObject(), any(Locale.class));
        assertThat(localizationService.getLocalValue("1")).isNull();
        assertThat(localizationService.getLocalValue("1", List.of("1"))).isNull();
    }
}
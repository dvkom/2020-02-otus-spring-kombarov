package ru.dvkombarov.app.rest.endpoints;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dvkombarov.app.exceptions.ReportParseException;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;
import ru.dvkombarov.app.service.ReportAnalyzer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalyzeController.class)
@AutoConfigureWebClient
@DisplayName("Methods of the analyze controller should ")
class AnalyzeControllerTest {


  @MockBean
  private ReportAnalyzer reportAnalyzer;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("return status ok when file is passed ")
  void shouldReturnStatusOkWhenFilePassed() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file", new ByteArrayInputStream("securityReport".getBytes())
    );
    doReturn(List.of(new VulnerInfoDto())).when(reportAnalyzer).analyze(any(InputStream.class));
    mockMvc.perform(MockMvcRequestBuilders
        .multipart("/api/upload")
        .file(multipartFile))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("return status 404 when passed file is wrong ")
  void shouldReturnStatus404WhenFileIncorrect() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file", new ByteArrayInputStream("securityReport".getBytes())
    );
    doThrow(ReportParseException.class).when(reportAnalyzer).analyze(any(InputStream.class));
    mockMvc.perform(MockMvcRequestBuilders
        .multipart("/api/upload")
        .file(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("return 500 status when an unexpected exception occurs ")
  void shouldReturnStatus500WhenOtherException() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file", new ByteArrayInputStream("securityReport".getBytes())
    );
    doThrow(RuntimeException.class).when(reportAnalyzer).analyze(any(InputStream.class));
    mockMvc.perform(MockMvcRequestBuilders
        .multipart("/api/upload")
        .file(multipartFile))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }
}
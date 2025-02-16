package site.haruhana.www.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import site.haruhana.www.dto.MonthlyUserSolveHistoryDTO;
import site.haruhana.www.service.UserService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {
        UserControllerIntegrationTest.TestConfig.class,
        UserController.class,
        GlobalExceptionHandler.class
})
@WithMockUser // 인증된 사용자로 처리하여 보안 리다이렉트 문제 해결
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Configuration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    @DisplayName("유효한 월 파라미터로 조회 시 200 OK를 반환")
    void testGetUserSolveHistoryReturnsOkForValidMonth() throws Exception {
        MonthlyUserSolveHistoryDTO testData = new MonthlyUserSolveHistoryDTO(Collections.emptyList());
        when(userService.getMonthlyUserSolveHistory(1L, 2025, 5)).thenReturn(testData);

        mockMvc.perform(get("/api/users/1/solve-history")
                        .param("year", "2025")
                        .param("month", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("유효하지 않은 월 파라미터로 조회 시 400 Bad Request를 반환")
    void testGetUserSolveHistoryReturnsBadRequestForInvalidMonth() throws Exception {
        mockMvc.perform(get("/api/users/1/solve-history")
                        .param("year", "2025")
                        .param("month", "13"))
                .andExpect(status().isBadRequest());
    }
}
package site.haruhana.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import site.haruhana.www.config.TestSecurityConfig;
import site.haruhana.www.dto.SignUpRequestDto;
import site.haruhana.www.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class SignUpValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Nested
    @DisplayName("이름 필드 유효성 검증")
    class NameValidation {

        @Test
        @DisplayName("이름이 null이면 검증에 실패한다")
        void test1() throws Exception {
            // given: 이름이 null인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name(null)
                    .email("test@example.com")
                    .password("password123")
                    .build();

            // when & then: 회원가입 API를 호출하면 400 Bad Request와 함께 이름 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("name"))
                    .andExpect(jsonPath("$.data[0].message").value("이름은 2자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 검증에 실패한다")
        void test2() throws Exception {
            // given: 이름이 빈 문자열인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("")
                    .email("test@example.com")
                    .password("password123")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 이름 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("name"))
                    .andExpect(jsonPath("$.data[0].message").value("이름은 2자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("이름이 2자 미만이면 검증에 실패한다")
        void test3() throws Exception {
            // given: 이름이 2자 미만인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("a")
                    .email("test@example.com")
                    .password("password123")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 이름 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("name"))
                    .andExpect(jsonPath("$.data[0].message").value("이름은 2자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("이름이 20자 초과이면 검증에 실패한다")
        void test4() throws Exception {
            // given: 이름이 20자 초과인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("a".repeat(21))
                    .email("test@example.com")
                    .password("password123")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 이름 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("name"))
                    .andExpect(jsonPath("$.data[0].message").value("이름은 2자 이상 20자 이하로 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("이메일 필드 유효성 검증")
    class EmailValidation {

        @Test
        @DisplayName("이메일이 null이면 검증에 실패한다")
        void test1() throws Exception {
            // given: 이메일이 null인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email(null)
                    .password("password123")
                    .build();

            // when & then: 회원가입 API를 호출하면 400 Bad Request와 함께 이메일 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("email"))
                    .andExpect(jsonPath("$.data[0].message").value("올바른 이메일 형식이 아닙니다."));
        }

        @Test
        @DisplayName("이메일이 빈 문자열이면 검증에 실패한다")
        void test2() throws Exception {
            // given: 이메일이 빈 문자열인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("")
                    .password("password123")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 이메일 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("email"))
                    .andExpect(jsonPath("$.data[0].message").value("올바른 이메일 형식이 아닙니다."));
        }

        @Test
        @DisplayName("이메일 형식이 올바르지 않으면 검증에 실패한다")
        void test3() throws Exception {
            // given: 이메일 형식이 올바르지 않은 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("invalid-email")
                    .password("password123")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 이메일 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("email"))
                    .andExpect(jsonPath("$.data[0].message").value("올바른 이메일 형식이 아닙니다."));
        }
    }

    @Nested
    @DisplayName("비밀번호 필드 유효성 검증")
    class PasswordValidation {

        @Test
        @DisplayName("비밀번호가 null이면 검증에 실패한다")
        void test1() throws Exception {
            // given: 비밀번호가 null인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("test@example.com")
                    .password(null)
                    .build();

            // when & then: 회원가입 API를 호출하면 400 Bad Request와 함께 비밀번호 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("password"))
                    .andExpect(jsonPath("$.data[0].message").value("비밀번호는 8자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("비밀번호가 빈 문자열이면 검증에 실패한다")
        void test2() throws Exception {
            // given: 비밀번호가 빈 문자열인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("test@example.com")
                    .password("")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 비밀번호 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("password"))
                    .andExpect(jsonPath("$.data[0].message").value("비밀번호는 8자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("비밀번호가 8자 미만이면 검증에 실패한다")
        void test3() throws Exception {
            // given: 비밀번호가 8자 미만인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("test@example.com")
                    .password("1234567")
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 비밀번호 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("password"))
                    .andExpect(jsonPath("$.data[0].message").value("비밀번호는 8자 이상 20자 이하로 입력해주세요."));
        }

        @Test
        @DisplayName("비밀번호가 20자 초과이면 검증에 실패한다")
        void test4() throws Exception {
            // given: 비밀번호가 20자 초과인 회원가입 요청이 주어질 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("test@example.com")
                    .password("a".repeat(21))
                    .build();

            // when: 회원가입 API를 호출하면
            // then: 400 Bad Request와 함께 비밀번호 필드 검증 실패 메시지를 응답한다
            mockMvc.perform(
                            post("/api/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[0].field").value("password"))
                    .andExpect(jsonPath("$.data[0].message").value("비밀번호는 8자 이상 20자 이하로 입력해주세요."));
        }
    }

    @Test
    @DisplayName("여러 필드가 동시에 유효하지 않으면 모든 검증 실패 메시지를 반환한다")
    void test2() throws Exception {
        // given: 여러 필드가 유효하지 않은 회원가입 요청이 주어질 때
        SignUpRequestDto request = SignUpRequestDto.builder()
                .name("a")  // 2자 미만
                .email("invalid-email")  // 잘못된 이메일 형식
                .password("123")  // 8자 미만
                .build();

        // when & then: 회원가입 API를 호출하면 400 Bad Request와 함께 모든 필드의 검증 실패 메시지를 응답한다
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field=='name')].message")
                        .value("이름은 2자 이상 20자 이하로 입력해주세요."))
                .andExpect(jsonPath("$.data[?(@.field=='email')].message")
                        .value("올바른 이메일 형식이 아닙니다."))
                .andExpect(jsonPath("$.data[?(@.field=='password')].message")
                        .value("비밀번호는 8자 이상 20자 이하로 입력해주세요."))
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    @DisplayName("모든 필드가 유효하면 검증에 성공한다")
    void test1() throws Exception {
        // given: 모든 필드가 유효한 회원가입 요청이 주어질 때
        SignUpRequestDto request = SignUpRequestDto.builder()
                .name("TestUser")
                .email("test@example.com")
                .password("password123")
                .build();

        // when: 회원가입 API를 호출하면
        // then: 201 Created 응답을 반환한다
        mockMvc.perform(
                        post("/api/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }
}

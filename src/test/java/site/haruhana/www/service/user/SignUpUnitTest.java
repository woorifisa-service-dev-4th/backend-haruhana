package site.haruhana.www.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.haruhana.www.dto.SignUpRequestDto;
import site.haruhana.www.dto.UserDto;
import site.haruhana.www.entity.AuthProvider;
import site.haruhana.www.entity.Role;
import site.haruhana.www.entity.User;
import site.haruhana.www.exception.DuplicateEmailException;
import site.haruhana.www.repository.UserRepository;
import site.haruhana.www.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SignUpUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("회원가입 성공 테스트")
    class SignUpSuccess {

        @Test
        @DisplayName("새로운 이메일로 일반 회원가입을 진행한다")
        void test1() {
            // given: 새로운 이메일을 가진 회원가입 요청이 주어졌을 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("test@example.com")
                    .password("password123")
                    .build();

            User savedUser = new User(request);
            given(userRepository.existsByProviderAndEmail(AuthProvider.LOCAL, request.getEmail())).willReturn(false);
            given(userRepository.save(any(User.class))).willReturn(savedUser);

            // when: 회원가입을 요청하면
            UserDto result = userService.signUp(request);

            // then: 사용자 정보가 정상적으로 저장되고 반환된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(request.getEmail()),
                    () -> assertThat(result.getName()).isEqualTo(request.getName()),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }
    }

    @Nested
    @DisplayName("회원가입 실패 테스트")
    class SignUpFailure {

        @Test
        @DisplayName("이미 가입된 이메일로 회원가입을 시도하면 예외가 발생한다")
        void test1() {
            // given: 이미 가입된 이메일로 회원가입 요청이 주어졌을 때
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("TestUser")
                    .email("existing@example.com")
                    .password("password123")
                    .build();

            given(userRepository.existsByProviderAndEmail(AuthProvider.LOCAL, request.getEmail())).willReturn(true);

            // when: 동일한 provider로 회원가입을 시도하면
            // then: 중복 이메일 예외가 발생한다
            assertThrows(DuplicateEmailException.class, () -> userService.signUp(request));
        }
    }
}
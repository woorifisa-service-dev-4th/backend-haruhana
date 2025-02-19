package site.haruhana.www.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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

@SpringBootTest
@Transactional
class SignUpIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("소셜 계정이 존재하지 않는 경우")
    class NoSocialAccounts {

        @Test
        @DisplayName("기존 계정이 없을 때 Local 계정으로 가입할 수 있다")
        void test1() {
            // given: 기존 계정이 없는 상황에서
            String email = "test@example.com";

            // when: 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }
    }

    @Nested
    @DisplayName("단일 소셜 계정이 존재하는 경우")
    class SingleSocialAccount {

        @Test
        @DisplayName("Google 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test1() {
            // given: Google 계정이 존재하는 상황에서
            String email = "test@example.com";

            User googleUser = User.builder()
                    .name("Google User")
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .profileImageUrl("https://google.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(googleUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }

        @Test
        @DisplayName("Kakao 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test2() {
            // given: Kakao 계정이 존재하는 상황에서
            String email = "test@example.com";

            User kakaoUser = User.builder()
                    .name("Kakao User")
                    .email(email)
                    .provider(AuthProvider.KAKAO)
                    .profileImageUrl("https://kakao.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(kakaoUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }

        @Test
        @DisplayName("Naver 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test3() {
            // given: Naver 계정이 존재하는 상황에서
            String email = "test@example.com";

            User naverUser = User.builder()
                    .name("Naver User")
                    .email(email)
                    .provider(AuthProvider.NAVER)
                    .profileImageUrl("https://naver.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(naverUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }
    }

    @Nested
    @DisplayName("두 개의 소셜 계정이 존재하는 경우")
    class TwoSocialAccounts {

        @Test
        @DisplayName("Google과 Kakao 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test1() {
            // given: Google과 Kakao 계정이 존재하는 상황에서
            String email = "test@example.com";

            User googleUser = User.builder()
                    .name("Google User")
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .profileImageUrl("https://google.com/photos/123")
                    .role(Role.USER)
                    .build();

            User kakaoUser = User.builder()
                    .name("Kakao User")
                    .email(email)
                    .provider(AuthProvider.KAKAO)
                    .profileImageUrl("https://kakao.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(googleUser);
            userRepository.save(kakaoUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }

        @Test
        @DisplayName("Google과 Naver 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test2() {
            // given: Google과 Naver 계정이 존재하는 상황에서
            String email = "test@example.com";

            User googleUser = User.builder()
                    .name("Google User")
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .profileImageUrl("https://google.com/photos/123")
                    .role(Role.USER)
                    .build();

            User naverUser = User.builder()
                    .name("Naver User")
                    .email(email)
                    .provider(AuthProvider.NAVER)
                    .profileImageUrl("https://naver.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(googleUser);
            userRepository.save(naverUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }

        @Test
        @DisplayName("Kakao와 Naver 계정이 있을 때 Local 계정으로 가입할 수 있다")
        void test3() {
            // given: Kakao와 Naver 계정이 존재하는 상황에서
            String email = "test@example.com";

            User kakaoUser = User.builder()
                    .name("Kakao User")
                    .email(email)
                    .provider(AuthProvider.KAKAO)
                    .profileImageUrl("https://kakao.com/photos/123")
                    .role(Role.USER)
                    .build();

            User naverUser = User.builder()
                    .name("Naver User")
                    .email(email)
                    .provider(AuthProvider.NAVER)
                    .profileImageUrl("https://naver.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(kakaoUser);
            userRepository.save(naverUser);

            // when: 동일한 이메일로 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }
    }

    @Nested
    @DisplayName("모든 소셜 계정이 존재하는 경우")
    class AllSocialAccounts {

        @Test
        @DisplayName("Google, Kakao, Naver 계정이 모두 있을 때 Local 계정으로 가입할 수 있다")
        void test1() {
            // given: 모든 소셜 계정이 존재하는 상황에서
            String email = "test@example.com";

            User googleUser = User.builder()
                    .name("Google User")
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .profileImageUrl("https://google.com/photos/123")
                    .role(Role.USER)
                    .build();

            User kakaoUser = User.builder()
                    .name("Kakao User")
                    .email(email)
                    .provider(AuthProvider.KAKAO)
                    .profileImageUrl("https://kakao.com/photos/123")
                    .role(Role.USER)
                    .build();

            User naverUser = User.builder()
                    .name("Naver User")
                    .email(email)
                    .provider(AuthProvider.NAVER)
                    .profileImageUrl("https://naver.com/photos/123")
                    .role(Role.USER)
                    .build();

            userRepository.save(googleUser);
            userRepository.save(kakaoUser);
            userRepository.save(naverUser);

            // when: 동일한 이메일로 Local 회원가입을 요청하면
            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("Local User")
                    .email(email)
                    .password("password123")
                    .build();

            UserDto result = userService.signUp(request);

            // then: 새로운 Local 계정이 정상적으로 생성된다
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo(email),
                    () -> assertThat(result.getName()).isEqualTo("Local User"),
                    () -> assertThat(result.getProvider()).isEqualTo(AuthProvider.LOCAL),
                    () -> assertThat(result.getRole()).isEqualTo(Role.USER)
            );
        }
    }

    @Nested
    @DisplayName("회원가입 실패 테스트")
    class SignUpFailure {

        @Test
        @DisplayName("이미 가입된 이메일로 회원가입을 시도하면 실패한다")
        void test1() {
            // given: 이미 가입된 계정이 있을 때
            String email = "test@example.com";

            User existingUser = User.builder()
                    .name("Existing User")
                    .email(email)
                    .provider(AuthProvider.LOCAL)
                    .rawPassword("existingPassword123")
                    .role(Role.USER)
                    .build();

            userRepository.save(existingUser);

            SignUpRequestDto request = SignUpRequestDto.builder()
                    .name("New User")
                    .email(email)
                    .password("password123")
                    .build();

            // when & then: 같은 이메일로 회원가입을 시도하면 예외가 발생한다
            assertThrows(DuplicateEmailException.class, () -> userService.signUp(request));
        }

    }

}
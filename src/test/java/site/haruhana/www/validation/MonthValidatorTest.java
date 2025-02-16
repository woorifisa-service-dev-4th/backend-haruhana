package site.haruhana.www.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class MonthValidatorTest {

    private MonthValidator monthValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        // 테스트 대상인 MonthValidator를 초기화합니다.
        monthValidator = new MonthValidator();
        // ConstraintValidatorContext는 실제 동작에 영향이 없으므로, 목(Mock) 객체를 사용합니다.
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @DisplayName("입력 월이 null인 경우, 검증 시 유효한 값으로 처리된다")
    @Test
    void givenNullMonth_whenValidating_thenShouldBeValid() {
        // Given: 입력 월이 null인 상황
        Integer month = null;

        // When: MonthValidator의 isValid() 메서드를 호출하면,
        boolean result = monthValidator.isValid(month, context);

        // Then: null 값은 다른 검증(@NotNull 등)으로 처리하도록 하여 true를 반환해야 한다.
        assertTrue(result, "null 월은 유효한 값으로 처리되어야 합니다.");
    }

    @DisplayName("유효한 월 값(1, 6, 12)이 입력되면, 검증 시 유효한 값으로 처리된다")
    @ParameterizedTest(name = "입력 월 {0}은 유효해야 한다")
    @ValueSource(ints = {1, 6, 12})
        // Given: 유효한 월 값 (예: 1, 6, 12)
    void givenValidMonth_whenValidating_thenShouldBeValid(int validMonth) {

        // When: 해당 월 값을 검증하면,
        boolean result = monthValidator.isValid(validMonth, context);

        // Then: 결과는 true여야 한다.
        assertTrue(result, "월 " + validMonth + "은 유효한 값이어야 합니다.");
    }

    @DisplayName("유효하지 않은 월 값(0, 13)이 입력되면, 검증 시 유효하지 않은 값으로 처리된다")
    @ParameterizedTest(name = "입력 월 {0}은 유효하지 않아야 한다")
    @ValueSource(ints = {0, 13})
        // Given: 유효하지 않은 월 값 (예: 0, 13)
    void givenInvalidMonth_whenValidating_thenShouldBeInvalid(int invalidMonth) {

        // When: 해당 월 값을 검증하면,
        boolean result = monthValidator.isValid(invalidMonth, context);

        // Then: 결과는 false여야 한다.
        assertFalse(result, "월 " + invalidMonth + "은 유효하지 않은 값이어야 합니다.");
    }
}

package site.haruhana.www.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StreakCalculatorTest {

    private final StreakCalculator streakCalculator = new StreakCalculator();

    @Test
    @DisplayName("문제를 풀지 않은 경우 연속 학습일은 0이어야 한다.")
    void shouldReturnZeroWhenNoAttempts() {
        // Given
        List<LocalDate> attemptDates = List.of();

        // When
        int maxStreak = streakCalculator.calculateMaxStreak(attemptDates);

        // Then
        assertThat(maxStreak).isEqualTo(0);
    }

    @Test
    @DisplayName("하루만 문제를 풀었을 경우 연속 학습일은 1이어야 한다.")
    void shouldReturnOneWhenSingleAttempt() {
        // Given
        List<LocalDate> attemptDates = List.of(LocalDate.of(2025, 2, 10));

        // When
        int maxStreak = streakCalculator.calculateMaxStreak(attemptDates);

        // Then
        assertThat(maxStreak).isEqualTo(1);
    }

    @Test
    @DisplayName("연속된 날짜에 문제를 풀었을 경우 최대 연속 학습일을 반환해야 한다.")
    void shouldReturnStreakForConsecutiveDays() {
        // Given
        List<LocalDate> attemptDates = List.of(
                LocalDate.of(2025, 2, 10),
                LocalDate.of(2025, 2, 11),
                LocalDate.of(2025, 2, 12)
        );

        // When
        int maxStreak = streakCalculator.calculateMaxStreak(attemptDates);

        // Then
        assertThat(maxStreak).isEqualTo(3);
    }

    @Test
    @DisplayName("연속되지 않은 날짜에 문제를 풀었을 경우 최대 연속 학습일을 올바르게 계산해야 한다.")
    void shouldReturnMaxStreakForNonConsecutiveDays() {
        // Given
        List<LocalDate> attemptDates = List.of(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 2),
                LocalDate.of(2025, 2, 3),
                LocalDate.of(2025, 2, 10),
                LocalDate.of(2025, 2, 11),
                LocalDate.of(2025, 2, 12),
                LocalDate.of(2025, 2, 13)
        );

        // When
        int maxStreak = streakCalculator.calculateMaxStreak(attemptDates);

        // Then
        assertThat(maxStreak).isEqualTo(4);
    }
}

package site.haruhana.www.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private final UserService userService = new UserService(null, null);

    @Nested
    @DisplayName("getMaxConsecutiveStudyDays 메서드 테스트")
    class GetMaxConsecutiveStudyDaysTest {

        @Test
        @DisplayName("사용자가 한 번도 학습하지 않았을 경우 최대 연속 학습일 수는 0이다")
        void shouldReturnZeroWhenNoStudyRecords() {
            // Given (사용자의 학습 기록이 없음)
            List<LocalDate> attemptDates = List.of();

            // When (연속 학습일 수 계산)
            int maxConsecutiveDays = userService.getMaxConsecutiveStudyDays(attemptDates);

            // Then (0을 반환해야 함)
            assertThat(maxConsecutiveDays).isEqualTo(0);
        }

        @Test
        @DisplayName("연속된 날짜로 학습했을 경우 최대 연속 학습일 수를 정확히 계산해야 한다")
        void shouldCalculateCorrectlyWhenDatesAreConsecutive() {
            // Given (연속된 날짜 학습 기록)
            List<LocalDate> attemptDates = List.of(
                    LocalDate.of(2024, 2, 10),
                    LocalDate.of(2024, 2, 11),
                    LocalDate.of(2024, 2, 12),
                    LocalDate.of(2024, 2, 13)
            );

            // When (연속 학습일 수 계산)
            int maxConsecutiveDays = userService.getMaxConsecutiveStudyDays(attemptDates);

            // Then (4일 연속 학습)
            assertThat(maxConsecutiveDays).isEqualTo(4);
        }

        @Test
        @DisplayName("연속되지 않은 날짜가 포함되었을 경우 최대 연속 학습일 수를 정확히 계산해야 한다")
        void shouldCalculateMaxStreakWhenDatesAreNotConsecutive() {
            // Given (연속되지 않은 날짜 포함)
            List<LocalDate> attemptDates = List.of(
                    LocalDate.of(2024, 2, 10),
                    LocalDate.of(2024, 2, 11),
                    LocalDate.of(2024, 2, 13), // 12일 건너뜀
                    LocalDate.of(2024, 2, 14),
                    LocalDate.of(2024, 2, 15)
            );

            // When (연속 학습일 수 계산)
            int maxConsecutiveDays = userService.getMaxConsecutiveStudyDays(attemptDates);

            // Then (가장 긴 연속 학습일은 3일)
            assertThat(maxConsecutiveDays).isEqualTo(3);
        }

        @Test
        @DisplayName("오늘 이후의 날짜는 고려하지 않고 연속 학습일을 계산해야 한다")
        void shouldIgnoreFutureDates() {
            // Given (오늘 이후의 날짜 포함)
            LocalDate today = LocalDate.now();
            List<LocalDate> attemptDates = List.of(
                    today.minusDays(2), // 이틀 전
                    today.minusDays(1), // 하루 전
                    today,              // 오늘
                    today.plusDays(1),  // 내일
                    today.plusDays(2)   // 모레
            );

            // When (연속 학습일 수 계산)
            int maxConsecutiveDays = userService.getMaxConsecutiveStudyDays(attemptDates);

            // Then (내일과 모레는 무시되므로 3일 연속 학습)
            assertThat(maxConsecutiveDays).isEqualTo(3);
        }
    }
}

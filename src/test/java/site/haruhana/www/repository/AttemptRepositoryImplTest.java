package site.haruhana.www.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site.haruhana.www.config.QuerydslConfig;
import site.haruhana.www.entity.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class AttemptRepositoryImplTest {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private EntityManager em;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 테스트 사용자 생성
        testUser = User.builder()
                .name("testUser")
                .email("test@example.com")
                .provider(AuthProvider.LOCAL)
                .role(Role.USER)
                .build();

        em.persist(testUser);

        // 2024년 1월에 대한 시도 데이터 생성
        createAttempt(LocalDate.of(2024, 1, 1));
        createAttempt(LocalDate.of(2024, 1, 1)); // 같은 날짜 중복
        createAttempt(LocalDate.of(2024, 1, 15));
        createAttempt(LocalDate.of(2024, 1, 31));

        // 2024년 2월 데이터 생성 (다른 월 데이터)
        createAttempt(LocalDate.of(2024, 2, 1));

        em.flush();
        em.clear();
    }

    private void createAttempt(LocalDate date) {
        // 더미 Problem 생성 with builder
        Problem problem = Problem.builder()
                .title("테스트 문제")
                .description("이것은 테스트를 위한 문제입니다.")
                .level(1)
                .answer("42")
                .problemCategory(ProblemCategory.BACKEND)
                .build();
        em.persist(problem);

        // 더미 Attempt 생성 with builder
        Attempt attempt = Attempt.builder()
                .problem(problem)
                .user(testUser)
                .date(date)
                .time(120)
                .userInput(42)
                .success(true)
                .build();

        em.persist(attempt);
    }

    @Test
    @DisplayName("특정 월의 중복 없는 시도 날짜를 조회한다")
    void findDistinctDatesInMonth() {
        // given: 2024년 1월에 1일(2회), 15일, 31일에 시도 기록이 주어졌을 때
        
        // when: 2024년 1월의 시도 날짜들을 조회하면
        List<LocalDate> dates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(testUser.getId(), 2024, 1);

        // then: 중복이 제거된 3개의 날짜가 순서대로 반환된다
        assertThat(dates).hasSize(3)
                .containsExactly(
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 1, 15),
                        LocalDate.of(2024, 1, 31)
                );
    }

    @Test
    @DisplayName("존재하지 않는 월의 데이터를 조회하면 빈 리스트를 반환한다")
    void findDatesInNonExistingMonth() {
        // given: 2024년 1월과 2월에만 시도 기록이 주어졌을 때
        
        // when: 데이터가 없는 3월을 조회하면
        List<LocalDate> dates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(testUser.getId(), 2024, 3);

        // then: 빈 리스트가 반환된다
        assertThat(dates).isEmpty();
    }

    @Test
    @DisplayName("날짜가 오름차순으로 정렬되어 반환된다")
    void findDatesInAscendingOrder() {
        // given: 1월 20일과 5일의 추가 시도 기록이 주어졌을 때
        createAttempt(LocalDate.of(2024, 1, 20));
        createAttempt(LocalDate.of(2024, 1, 5));
        em.flush();
        em.clear();

        // when: 2024년 1월의 모든 시도 날짜를 조회하면
        List<LocalDate> dates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(testUser.getId(), 2024, 1);

        // then: 5개의 날짜가 오름차순으로 정렬되어 반환된다
        assertThat(dates)
                .isSortedAccordingTo(LocalDate::compareTo)
                .hasSize(5)
                .containsExactly(
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 1, 5),
                        LocalDate.of(2024, 1, 15),
                        LocalDate.of(2024, 1, 20),
                        LocalDate.of(2024, 1, 31)
                );
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다")
    void findDatesWithNonExistingUserId() {
        // given: 존재하지 않는 사용자 ID가 주어졌을 때
        
        // when: 존재하지 않는 사용자의 시도 날짜를 조회하면
        List<LocalDate> dates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(999L, 2024, 1);

        // then: 빈 리스트가 반환된다
        assertThat(dates).isEmpty();
    }

    @Test
    @DisplayName("월이 변경되는 경계값 테스트")
    void findDatesAtMonthBoundary() {
        // given: 1월 31일과 2월 1일의 시도 기록이 주어졌을 때
        createAttempt(LocalDate.of(2024, 1, 31));
        createAttempt(LocalDate.of(2024, 2, 1));
        em.flush();
        em.clear();

        // when: 2024년 2월의 시도 날짜를 조회하면
        List<LocalDate> februaryDates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(testUser.getId(), 2024, 2);

        // then: 2월 1일만 포함된 리스트가 반환된다
        assertThat(februaryDates)
                .hasSize(1)
                .containsExactly(LocalDate.of(2024, 2, 1));
    }

    @Test
    @DisplayName("윤년의 2월 날짜 처리 테스트")
    void findDatesInLeapYearFebruary() {
        // given: 윤년 2024년 2월 29일의 시도 기록이 주어졌을 때
        createAttempt(LocalDate.of(2024, 2, 29));
        em.flush();
        em.clear();

        // when: 2024년 2월의 시도 날짜를 조회하면
        List<LocalDate> dates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(testUser.getId(), 2024, 2);

        // then: 2월 29일이 포함된 정렬된 리스트가 반환된다
        assertThat(dates)
                .contains(LocalDate.of(2024, 2, 29))
                .isSortedAccordingTo(LocalDate::compareTo);
    }
}
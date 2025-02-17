package site.haruhana.www.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.haruhana.www.entity.QAttempt;

import java.time.LocalDate;
import java.util.List;

/**
 * AttemptRepositoryCustom 인터페이스의 구현체
 * QueryDSL을 사용하여 복잡한 쿼리를 구현
 */
@RequiredArgsConstructor
public class AttemptRepositoryImpl implements AttemptRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<LocalDate> findDistinctAttemptDatesByUserIdAndYearMonth(Long userId, int year, int month) {

        // Q클래스 인스턴스 생성
        QAttempt attempt = QAttempt.attempt;

        // 해당 월의 시작일과 종료일 계산
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // QueryDSL을 사용한 쿼리 생성 및 실행
        return queryFactory
                .select(attempt.date) // 날짜만 조회
                .distinct() // 중복 제거
                .from(attempt) // attempts 테이블에서
                .where(
                        attempt.user.id.eq(userId) // 특정 사용자의
                                .and(attempt.date.between(startDate, endDate)) // 특정 기간 내의 데이터
                )
                .orderBy(attempt.date.asc()) // 날짜 오름차순 정렬
                .fetch(); // 결과 조회
    }
}

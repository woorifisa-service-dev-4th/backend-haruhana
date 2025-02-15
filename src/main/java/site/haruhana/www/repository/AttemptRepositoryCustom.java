package site.haruhana.www.repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Attempt 엔티티에 대한 커스텀 쿼리 메소드를 정의하는 인터페이스
 * QueryDSL을 사용한 복잡한 쿼리 작성을 위한 인터페이스
 */
public interface AttemptRepositoryCustom {

    /**
     * 특정 사용자의 특정 년월에 대한 문제 풀이 시도 날짜를 조회
     * 중복된 날짜는 제거하여 반환
     *
     * @param userId 조회할 사용자의 ID
     * @param year   조회할 년도
     * @param month  조회할 월
     * @return 중복이 제거된 문제 풀이 시도 날짜 목록
     */
    List<LocalDate> findDistinctAttemptDatesByUserIdAndYearMonth(Long userId, int year, int month);
}

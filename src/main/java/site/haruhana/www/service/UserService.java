package site.haruhana.www.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.haruhana.www.dto.MonthlyUserSolveHistoryDTO;
import site.haruhana.www.dto.MonthlyUserSolveHistoryDTO.DailySolveStatus;
import site.haruhana.www.dto.SignUpRequestDto;
import site.haruhana.www.dto.UserDto;
import site.haruhana.www.entity.AuthProvider;
import site.haruhana.www.entity.User;
import site.haruhana.www.exception.DuplicateEmailException;
import site.haruhana.www.repository.AttemptRepository;
import site.haruhana.www.repository.UserRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AttemptRepository attemptRepository;

    @Transactional
    public UserDto signUp(SignUpRequestDto request) {
        // 이메일 중복 확인
        if (userRepository.existsByProviderAndEmail(AuthProvider.LOCAL, request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        User savedUser = userRepository.save(new User(request));
        return new UserDto(savedUser);
    }

    /**
     * 특정 사용자의 월별 문제 풀이 기록을 조회하는 메소드
     * 해당 월의 각 날짜별로 문제 풀이 여부를 확인하여 반환
     *
     * @param userId 조회할 사용자의 ID
     * @param year   조회할 연도
     * @param month  조회할 월
     * @return 해당 월의 일별 문제 풀이 여부가 담긴 DTO
     */
    @Transactional(readOnly = true)
    public MonthlyUserSolveHistoryDTO getMonthlyUserSolveHistory(Long userId, int year, int month) {
        // 해당 사용자가 해당 월에 문제를 푼 날짜들을 DB에서 조회
        List<LocalDate> attemptDates = attemptRepository
                .findDistinctAttemptDatesByUserIdAndYearMonth(userId, year, month);

        // 입력받은 연월의 시작일과 마지막일을 계산
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 날짜별 풀이 여부를 담을 리스트 초기화
        List<DailySolveStatus> solveHistory = new ArrayList<>();

        // 시작일부터 마지막 날짜까지 순차적으로 각 날짜의 풀이 여부를 확인
        // 캘린더에서 1일부터 순서대로 표시하기 위해 정순으로 데이터 생성
        LocalDate currentDate = startDate;

        // 마지막일까지 모든 날짜를 순회하며 풀이 여부 기록
        while (!currentDate.isAfter(endDate)) {
            solveHistory.add(new DailySolveStatus(
                    currentDate.format(DateTimeFormatter.ISO_DATE),  // yyyy-MM-dd 형식의 날짜 문자열로 변환
                    attemptDates.contains(currentDate)               // 해당 날짜에 풀이 기록이 있는지 확인
            ));
            currentDate = currentDate.plusDays(1);  // 다음 날짜로 이동
        }

        // 최종 결과 반환
        return new MonthlyUserSolveHistoryDTO(solveHistory);
    }
}

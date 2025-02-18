package site.haruhana.www.utils;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

@Component
public class StreakCalculator {

    /**
     * 연속된 학습일 수를 계산하는 메소드
     *
     * @param attemptDates 사용자가 문제를 푼 날짜 리스트
     * @return 최대 연속 학습일 수
     */
    public int calculateMaxStreak(List<LocalDate> attemptDates) {
        if (attemptDates.isEmpty()) {
            return 0;
        }

        // 정렬된 Set으로 변환
        TreeSet<LocalDate> sortedDates = new TreeSet<>(attemptDates);

        int maxStreak = 0;
        int currentStreak = 0;
        LocalDate prevDate = null;

        for (LocalDate date : sortedDates) {
            if (prevDate != null && prevDate.plusDays(1).equals(date)) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
            prevDate = date;
        }

        // 마지막 streak 체크
        maxStreak = Math.max(maxStreak, currentStreak);

        return maxStreak;
    }
}

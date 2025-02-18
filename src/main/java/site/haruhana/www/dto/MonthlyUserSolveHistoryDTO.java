package site.haruhana.www.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyUserSolveHistoryDTO {
    private final List<DailySolveStatus> solveHistory;
    private final int maxStreak; // 연속 학습일

    @Getter
    public static class DailySolveStatus {
        private final String date;
        private final boolean solved;

        public DailySolveStatus(String date, boolean solved) {
            this.date = date;
            this.solved = solved;
        }
    }

    public MonthlyUserSolveHistoryDTO(List<DailySolveStatus> solveHistory, int maxStreak) {
        this.solveHistory = solveHistory;
        this.maxStreak = maxStreak;
    }
}

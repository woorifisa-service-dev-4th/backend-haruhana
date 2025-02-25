package site.haruhana.www.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyUserSolveHistoryDTO {
    private final List<DailySolveStatus> solveHistory;
    private final int maxConsecutiveStudyDays;

    @Getter
    public static class DailySolveStatus {
        private final String date;
        private final boolean solved;

        public DailySolveStatus(String date, boolean solved) {
            this.date = date;
            this.solved = solved;
        }
    }

    public MonthlyUserSolveHistoryDTO(List<DailySolveStatus> solveHistory, int maxConsecutiveStudyDays) {
        this.solveHistory = solveHistory;
        this.maxConsecutiveStudyDays = maxConsecutiveStudyDays;
    }
}

package site.haruhana.www.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyUserSolveHistoryDTO {
    private final List<DailySolveStatus> solveHistory;

    @Getter
    public static class DailySolveStatus {
        private final String date;
        private final boolean solved;

        public DailySolveStatus(String date, boolean solved) {
            this.date = date;
            this.solved = solved;
        }
    }

    public MonthlyUserSolveHistoryDTO(List<DailySolveStatus> solveHistory) {
        this.solveHistory = solveHistory;
    }
}

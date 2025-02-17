package site.haruhana.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.entity.ProblemCategory;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProblemDto {
    private Long id;
    private String title;
    private String description;
    private String answer;
    private int level;
    private ProblemCategory problemCategory;

    public ProblemDto(Problem problem) {
        this.id = problem.getId();
        this.title = problem.getTitle();
        this.description = problem.getDescription();
        this.answer = problem.getAnswer();
        this.level = problem.getLevel();
        this.problemCategory = getProblemCategory();
    }

}

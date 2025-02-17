package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.haruhana.www.dto.ProblemDto;

@Entity
@Table(name = "problems")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem extends BaseTimeEntity {

    /**
     * 문제 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    /**
     * 문제 제목
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 문제 설명
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * 문제 난이도
     */
    @Column(name = "problem_level", columnDefinition = "TINYINT")
    private int level;

    /**
     * 문제 정답
     */
    @Column(columnDefinition = "INT")
    private String answer;

    /**
     * 문제 카테고리
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private ProblemCategory problemCategory;

    @Builder
    public Problem(String title, String description, int level, String answer, ProblemCategory problemCategory) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.answer = answer;
        this.problemCategory = problemCategory;
    }

    /**
     * 문제 정보를 갱신하는 메소드
     *
     * @param updatedProblemDto 갱신할 문제 정보를 포함하는 DTO
     */
    public void update(ProblemDto updatedProblemDto) {
        this.title = updatedProblemDto.getTitle();
        this.description = updatedProblemDto.getDescription();
        this.level = updatedProblemDto.getLevel();
        this.problemCategory = updatedProblemDto.getProblemCategory();
    }

}
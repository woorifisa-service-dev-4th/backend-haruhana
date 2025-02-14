package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problems")
@Getter
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
     * @param title           새로운 문제 제목
     * @param description     새로운 문제 설명
     * @param level           새로운 문제 난이도
     * @param problemCategory 새로운 문제 카테고리
     */
    public void updateProblem(String title, String description, Integer level, ProblemCategory problemCategory) { // 필요한 값만 전달
        this.title = title;
        this.description = description;
        this.level = level;
        this.problemCategory = problemCategory;
    }
}
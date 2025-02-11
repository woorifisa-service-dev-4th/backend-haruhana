package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="problems")
public class Problem {

    /**
     * 문제 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    /**
     * 해당 문제의 카테고리 분류
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    /* 하나의 카테고리에는 여러개의 문제 속할 수 있음 (양방향) */

    /**
     * 문제 제목
     */
    private String title;

    /**
     * 문제 설명
     */
    private String description;

    /**
     * 문제 난이도
     */
    private Integer problemLevel;

    /**
     * 문제 정답
     */
    private String answer;

    /**
     * 문제가 생성된 날짜
     */
    private Date problemDate;


}

package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="problems")
public class Problems  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    /* 하나의 카테고리에는 여러개의 문제 속할 수 있음 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 해당 문제의 카테고리 분류

    private String title; // 문제 제목
    private String description; // 문제 설명
    private Integer problemLevel; // 문제 난이도
    private String answer; // 정답
    private Date problemDate; // 문제가 생성된 날짜


}

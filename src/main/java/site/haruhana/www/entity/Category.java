package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    /**
     * 카테고리 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    /**
     * Network, OS 등의 문제 별 카테고리 분류
     */
    private String categoryName;

    /**
     * 카테고리 별 문제 목록
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Problem> problems = new ArrayList<>();
    /* 카테고리에서 문제 목록을 가져오기 위해 양방향 관계로 설정 */
}

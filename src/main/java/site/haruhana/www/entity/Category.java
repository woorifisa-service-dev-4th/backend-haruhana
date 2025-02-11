package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Network, OS 등의 문제 별 카테고리 분류
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String categoryName; // Network, OS 등의 문제 별 카테고리 분류

    // 카테고리에서 문제 목록을 가져오기 위해 양방향 관계로 설정
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Problems> problems = new ArrayList<>();
}

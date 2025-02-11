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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String categoryName; // Network, OS 등의 문제 별 카테고리 분류

    /**
     * 해당 카테고리에 속한 문제들 (카테고리 삭제 시 문제들도 삭제됨)
     */
    @OneToMany(mappedBy = "categories",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Problems> problems = new ArrayList<>();

    @OneToMany(mappedBy = "categories",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserProficiency> userProficiencies = new ArrayList<>();

}

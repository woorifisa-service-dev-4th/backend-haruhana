package site.haruhana.www.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 객체의 불변성을 유지하기 위해 Setter를 사용하지 않습니다.

@Entity
@Table(name = "problems")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(nullable = false)
    @Size(max = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "problem_level", columnDefinition = "TINYINT")
    private Integer level;

    @Column(columnDefinition = "INTEGER")
    private String answer;


    @Enumerated(EnumType.STRING)
    @Column(name="category_id")
    private Category category;

   @Enumerated(EnumType.STRING)
    public void updateProblem(Problem target) {
        this.title = target.getTitle();
        this.description = target.getDescription();
        this.level = target.getLevel();
    }
}
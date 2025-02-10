package site.haruhana.www.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


//Setter 쓰지 말래

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

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "problem_level", columnDefinition = "TINYINT")
    private Integer level;

    @Column(columnDefinition = "VARCHAR(255)")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public void updateProblem(Problem target) {
        this.title = target.getTitle();
        this.description = target.getDescription();
        this.level = target.getLevel();
    }
}
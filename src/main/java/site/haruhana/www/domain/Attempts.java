package site.haruhana.www.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "attempts")
public class Attempts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attemptId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}

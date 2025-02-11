package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Duration;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="attempts")
public class Attempt {

    /**
     * 풀이 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long id;

    /**
     * 풀이 시도한 문제 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Problem problem;
    /* 하나의 문제에 대해 여러번의 풀이 진행 가능 (단방향) */

    /**
     * 풀이 시도한 사용자 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    /* 한명의 사용자가 여러번의 풀이 진행 가능 (양방향) */

    /**
     * 풀이 시도 날짜
     */
    private Date attemptDate;

    /**
     * 문제 푸는데 걸린 시간
     */
    private Duration attemptTime;

    /**
     * 사용자 제출 값
     */
    private Integer userInput;

    /**
     * 같은 문제에 대한 재시도 횟수
     */
    private Integer retryCount;

    /**
     * 풀이의 성공 여부
     */
    private Boolean success;

}

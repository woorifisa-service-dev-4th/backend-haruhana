package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 한 문제에 대한 사용자 풀이 기록
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="attempts")
public class Attempts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long id; // 풀이 id

    /* 하나의 문제에 대해 여러번의 풀이 진행 가능 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problems problem; // 풀이 시도한 문제 정보

    /* 한명의 사용자가 여러번의 풀이 진행 가능 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 풀이 시도한 사용자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_proficiency_id")
    private UserProficiency userProficiency;

    private Date attemptDate; // 풀이 시도 날짜
    private Duration attemptTime; // 문제 푸는데 걸린 시간

    private Integer userInput; // 사용자 제출 값
    private Integer retryCount; // 같은 문제에 대한 재시도 횟수
    private Boolean success; // 풀이의 성공 여부

}

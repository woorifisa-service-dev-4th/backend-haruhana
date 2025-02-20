package site.haruhana.www.service.problem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.entity.ProblemCategory;
import site.haruhana.www.repository.ProblemRepository;
import site.haruhana.www.service.ProblemService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DisplayName("문제 목록 조회 테스트")
class GetProblemListUnitTest {

    @InjectMocks
    private ProblemService problemService;

    @Mock
    private ProblemRepository problemRepository;

    @Nested
    @DisplayName("정상 케이스")
    class SuccessCase {

        @Test
        @DisplayName("빈 문제 목록을 조회하면 빈 페이지가 반환된다")
        void test1() {
            // given: 빈 문제 목록이 주어질 때
            Pageable pageable = PageRequest.of(0, 10);
            given(problemRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(new ArrayList<>()));

            // when: 문제 목록을 조회하면
            Page<ProblemDto> result = problemService.getAllProblems(pageable);

            // then: 빈 페이지가 반환된다
            assertAll(
                    () -> assertThat(result.getContent()).isEmpty(),
                    () -> assertThat(result.getTotalElements()).isZero()
            );
        }

        @Test
        @DisplayName("단일 문제가 존재할 때 페이지를 조회하면 하나의 문제가 반환된다")
        void test2() {
            // given: 하나의 문제가 주어질 때
            Pageable pageable = PageRequest.of(0, 10);
            Problem problem = Problem.builder()
                    .title("테스트 문제")
                    .description("설명")
                    .level(1)
                    .answer(42)
                    .problemCategory(ProblemCategory.BACKEND)
                    .build();
            List<Problem> problems = List.of(problem);
            given(problemRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(problems));

            // when: 문제 목록을 조회하면
            Page<ProblemDto> result = problemService.getAllProblems(pageable);

            // then: 하나의 문제가 반환된다
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(result.getContent().get(0).getTitle()).isEqualTo("테스트 문제")
            );
        }

        @Test
        @DisplayName("여러 문제가 존재할 때 첫 페이지를 조회하면 페이지 크기만큼의 문제가 반환된다")
        void test3() {
            // given: 여러 문제가 주어질 때
            Pageable pageable = PageRequest.of(0, 2);
            List<Problem> problems = List.of(
                    Problem.builder()
                            .title("문제1")
                            .description("설명1")
                            .level(1)
                            .answer(10)
                            .problemCategory(ProblemCategory.BACKEND)
                            .build(),

                    Problem.builder()
                            .title("문제2")
                            .description("설명2")
                            .level(2)
                            .answer(20)
                            .problemCategory(ProblemCategory.DATABASE)
                            .build()
            );
            given(problemRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(problems));

            // when: 문제 목록을 조회하면
            Page<ProblemDto> result = problemService.getAllProblems(pageable);

            // then: 페이지 크기만큼의 문제가 반환된다
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(2),
                    () -> assertThat(result.getContent().get(0).getTitle()).isEqualTo("문제1"),
                    () -> assertThat(result.getContent().get(1).getTitle()).isEqualTo("문제2")
            );
        }

        @Test
        @DisplayName("문제가 존재할 때 두 번째 페이지를 조회하면 해당 페이지의 문제가 반환된다")
        void test4() {
            // given: 두 번째 페이지 요청이 주어질 때
            Pageable pageable = PageRequest.of(1, 2);
            List<Problem> problems = List.of(
                    Problem.builder()
                            .title("문제3")
                            .description("설명3")
                            .level(3)
                            .answer(30)
                            .problemCategory(ProblemCategory.NETWORK)
                            .build()
            );
            given(problemRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(problems, pageable, 3));

            // when: 문제 목록을 조회하면
            Page<ProblemDto> result = problemService.getAllProblems(pageable);

            // then: 해당 페이지의 문제가 반환된다
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(result.getNumber()).isEqualTo(1),
                    () -> assertThat(result.getTotalElements()).isEqualTo(3)
            );
        }

        @Test
        @DisplayName("문제의 모든 필드가 정상적으로 매핑되어 반환된다")
        void test5() {
            // given: 모든 필드가 설정된 문제가 주어질 때
            Pageable pageable = PageRequest.of(0, 1);
            Problem problem = Problem.builder()
                    .problemCategory(ProblemCategory.BACKEND)
                    .title("알고리즘 문제")
                    .description("이진 탐색 구현")
                    .level(3)
                    .answer(100)
                    .build();

            given(problemRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(List.of(problem)));

            // when: 문제 목록을 조회하면
            Page<ProblemDto> result = problemService.getAllProblems(pageable);
            ProblemDto dto = result.getContent().get(0);

            // then: 모든 필드가 정확히 매핑되어 반환된다
            assertAll(
                    () -> assertThat(dto.getTitle()).isEqualTo("알고리즘 문제"),
                    () -> assertThat(dto.getDescription()).isEqualTo("이진 탐색 구현"),
                    () -> assertThat(dto.getLevel()).isEqualTo(3),
                    () -> assertThat(dto.getAnswer()).isEqualTo(100),
                    () -> assertThat(dto.getProblemCategory()).isEqualTo(ProblemCategory.BACKEND)
            );
        }
    }
}
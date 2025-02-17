package site.haruhana.www.Service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import site.haruhana.www.converter.ProblemConverter;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.entity.ProblemCategory;
import site.haruhana.www.exception.ProblemNotFoundException;
import site.haruhana.www.repository.ProblemRepository;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {

    // ProblemRepository를 목(mock)으로 생성하여 ProblemService에 주입
    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private ProblemConverter problemConverter;
   
    @InjectMocks
    private ProblemService problemService;

    private Problem createTestProblem() {
        return Problem.builder()
                .title("테스트 문제")
                .description("테스트 설명")
                .level(1)
                .problemCategory(ProblemCategory.BACKEND)
                .answer("테스트 답안")
                .build();
    }

    @Test
    @DisplayName("ID로 문제를 조회하면 해당 문제를 반환한다")
    void getProblemById() {
        // given: 문제 ID와 예상되는 문제가 주어졌을 때
        Long problemId = 1L;
        Problem problem = createTestProblem();  // new Problem() 대신 이 메서드 사용
        ProblemDto expectedDto = new ProblemDto();
        given(problemRepository.findById(problemId)).willReturn(Optional.of(problem));
        given(problemConverter.toDto(problem)).willReturn(expectedDto); // 추가

        // when: ID로 문제를 조회하면
        ProblemDto result = problemService.getProblemById(problemId);

        // then: 해당하는 문제 DTO가 반환된다
        assertNotNull(result, "반환된 문제는 null이 아니어야 합니다");
        verify(problemRepository).findById(problemId);
        verify(problemConverter).toDto(problem); // 추가
    }

    @Test
    @DisplayName("존재하지 않는 ID로 문제를 조회하면 예외가 발생한다")
    void getProblemByIdNotFound() {
        // given: 존재하지 않는 문제 ID가 주어졌을 때
        Long nonExistentId = 999L;
        given(problemRepository.findById(nonExistentId)).willReturn(Optional.empty());

        // when & then: ID로 문제를 조회하면 ProblemNotFoundException이 발생한다
        assertThrows(ProblemNotFoundException.class, 
            () -> problemService.getProblemById(nonExistentId),
            "존재하지 않는 ID 조회 시 ProblemNotFoundException이 발생해야 합니다"
        );
    }

    @Test
    @DisplayName("카테고리별 문제 목록을 조회하면 해당 카테고리의 문제들을 반환한다")
    void getProblemsByCategory() {
        // given: 카테고리와 페이지 정보가 주어졌을 때
        ProblemCategory category = ProblemCategory.BACKEND;
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Problem> problems = List.of(createTestProblem(), createTestProblem());
        Page<Problem> problemPage = new PageImpl<>(problems);
        given(problemRepository.findByProblemCategory(category, pageRequest)).willReturn(problemPage);

        // when: 카테고리로 문제를 조회하면
        Page<Problem> result = problemService.getProblemsByCategory(category, pageRequest);

        // then: 해당 카테고리의 문제 목록이 반환된다
        assertNotNull(result, "반환된 페이지는 null이 아니어야 합니다");
        assertEquals(2, result.getContent().size(), "반환된 문제 수가 일치해야 합니다");
        verify(problemRepository).findByProblemCategory(category, pageRequest);
    }

    @Test
    @DisplayName("문제를 수정하면 업데이트된 문제를 반환한다")
    void updateProblem() {
        // given: 수정할 문제 ID와 업데이트할 내용이 주어졌을 때
        Long problemId = 1L;
        Problem existingProblem = createTestProblem();
        ProblemDto updateDto = new ProblemDto();
        ProblemDto resultDto = new ProblemDto();
        
        given(problemRepository.findById(problemId)).willReturn(Optional.of(existingProblem));
        given(problemRepository.save(any(Problem.class))).willReturn(existingProblem);
        given(problemConverter.toDto(any(Problem.class))).willReturn(resultDto); // 추가

        // when: 문제를 수정하면
        ProblemDto result = problemService.updateProblem(problemId, updateDto);

        // then: 업데이트된 문제가 반환된다
        assertNotNull(result, "업데이트된 문제는 null이 아니어야 합니다");
        verify(problemRepository).findById(problemId);
        verify(problemRepository).save(any(Problem.class));
        verify(problemConverter).toDto(any(Problem.class)); // 추가
    }

    @Test
    @DisplayName("문제를 삭제하면 성공적으로 처리된다")
    void deleteProblem() {
        // given: 삭제할 문제 ID가 주어졌을 때
        Long problemId = 1L;
        given(problemRepository.existsById(problemId)).willReturn(true);
        willDoNothing().given(problemRepository).deleteById(problemId);

        // when: 문제를 삭제하면
        problemService.deleteProblem(problemId);

        // then: 삭제가 정상적으로 수행된다
        verify(problemRepository).existsById(problemId);
        verify(problemRepository).deleteById(problemId);
    }

    @Test
    @DisplayName("모든 문제 목록을 조회하면 페이지네이션된 문제 목록을 반환한다")
    void getAllProblems() {
        // given: 페이지 정보와 예상되는 문제 목록이 주어졌을 때
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Problem> problems = List.of(createTestProblem(), createTestProblem());
        Page<Problem> problemPage = new PageImpl<>(problems);
        
        given(problemRepository.findAll(pageRequest)).willReturn(problemPage);
        
        // when: 모든 문제를 조회하면
        Page<ProblemDto> result = problemService.getAllProblems(pageRequest);
        
        // then: 페이지네이션된 문제 목록이 반환된다
        assertNotNull(result, "반환된 페이지는 null이 아니어야 합니다");
        assertEquals(2, result.getContent().size(), "반환된 문제 수가 일치해야 합니다");
        verify(problemRepository).findAll(pageRequest);
    }
}

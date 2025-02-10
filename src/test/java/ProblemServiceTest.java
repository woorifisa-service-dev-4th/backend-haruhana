import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.repository.ProblemRepository;
import site.haruhana.www.service.ProblemService;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {

    @Mock
    private ProblemRepository problemRepository;

//    @InjectMocks
    @Mock
    private ModelMapper modelMapper;
    private ProblemService problemService;

    @BeforeEach
    void setUp() {
        // Method 실행하기 전에 실행
        // repository mock
        // when(problemRepository.findAll()).thenReturn(Arrays.asList(new Problem(), new Problem()));
        problemService = new ProblemService(problemRepository, modelMapper);
    }

//    @Test
//    void 문제_id_를_기준으로_문제를_조회했을_떄 () {
//
//
//    }

    @Test
    void updateProblem() {
        Problem existingProblem = new Problem(1L, "Old Title", "Old description", 3, "Old answer", null);
        Problem newProblemDetails = new Problem(null, "New Title", "New description", 3, "New answer", null);

        when(problemRepository.findById(1L)).thenReturn(Optional.of(existingProblem));
        when(problemRepository.save(any(Problem.class))).thenAnswer(i -> i.getArgument(0));

        Problem updatedProblem = problemService.updateProblem(1L, newProblemDetails);
        assertNotNull(updatedProblem);
        assertEquals("New Title", updatedProblem.getTitle());
    }

    @Test
    void deleteProblem() {
        doNothing().when(problemRepository).deleteById(1L);
        problemService.deleteProblem(1L);
        verify(problemRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProblemsByCategoryId() {
        when(problemRepository.findByCategoryId(1L)).thenReturn(Arrays.asList(new Problem(), new Problem()));
        List<Problem> problems = problemService.getProblemsByCategoryId(1L);
        assertNotNull(problems);
        assertEquals(2, problems.size());
    }

    // test code는 내가 이게 제대로 동작할지 모르는 경우 -> 작성

    // jpa는 웬만해서 믿는다 (그렇다고 절대적으로 믿으면 안됨)
    //


}
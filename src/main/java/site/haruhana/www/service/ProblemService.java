package site.haruhana.www.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.repository.ProblemRepository;
import org.modelmapper.ModelMapper;


@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ModelMapper modelMapper;
    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(Long id) {
        return problemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Problem not found"));
    }

    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    public Problem updateProblem(Long id, Problem updatedProblem) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Problem not found"));

        problem.updateProblem(updatedProblem);

        return problemRepository.save(problem);
    }

    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    public List<Problem> getProblemsByCategoryId(Long categoryId) {
        return problemRepository.findByCategoryId(categoryId);
    }

}


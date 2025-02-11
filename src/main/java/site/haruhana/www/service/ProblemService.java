package site.haruhana.www.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.repository.ProblemRepository;
import org.modelmapper.ModelMapper;


@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ModelMapper modelMapper;
    private final ProblemRepository problemRepository;

    // 모든 문제 검색하여 반환
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }


    // 주어진 ID의 문제를 dto 로 변환하여 반환
    public ProblemDto getProblemDtoById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Problem not found"));
        return modelMapper.map(problem, ProblemDto.class);
    }


   // 문제 생성
    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }


    // 문제 업데이트
    public Problem updateProblem(Long id, Problem updatedProblem) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Problem not found"));

        problem.updateProblem(updatedProblem);

        return problemRepository.save(problem);
    }

    // 문제 삭제
    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    // 카테고리별로 문제 반환
    public Page<Problem> getProblemsByCategoryId(Long categoryId, Pageable pageable) {
        return problemRepository.findByCategoryId(categoryId, pageable);
    }

}




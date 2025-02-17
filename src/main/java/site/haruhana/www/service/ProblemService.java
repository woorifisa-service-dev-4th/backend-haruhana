package site.haruhana.www.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.ProblemCategory;
import site.haruhana.www.exception.ProblemNotFoundException;
import site.haruhana.www.repository.ProblemRepository;
import site.haruhana.www.converter.ProblemConverter;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final ProblemConverter problemConverter;

    /**
     * 모든 문제 검색하여 반환하는 메소드
     *
     * @param pageable 페이지네이션 정보를 포함하는 pageable 객체
     * @return 페이지네이션된 문제 DTO 페이지
     */
    public Page<ProblemDto> getAllProblems(Pageable pageable) {
        return problemRepository.findAll(pageable)
                .map(ProblemDto::new);
    }

    /**
     * 주어진 ID의 문제를 DTO 로 변환하여 반환하는 메소드
     *
     * @param id 검색할 문제의 ID
     * @return 변환된 문제 DTO
     */
    public ProblemDto getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ProblemNotFoundException("문제를 찾을 수 없습니다."));
        log.info("문제 조회 완료 - 문제 ID: {}", id);
        return problemConverter.toDto(problem);
    }

    /**
     * 새 문제 생성하는 메소드
     *
     * @param problemDto 저장할 문제 DTO
     * @return 저장된 문제 DTO
     */
    public ProblemDto createProblem(ProblemDto problemDto) {
        log.info("신규 문제 생성 시작 - 제목: {}", problemDto.getTitle());
        Problem problem = problemConverter.toEntity(problemDto);

        Problem createdProblem = problemRepository.save(problem);
        log.info("문제 생성 완료 - 문제 ID: {}", createdProblem.getId());

        return problemConverter.toDto(createdProblem);
    }

    /**
     * 기존의 문제를 업데이트하는 메소드
     *
     * @param id            업데이트할 문제의 ID
     * @param newProblemDto 업데이트할 문제 정보가 담긴 엔티티
     * @return 업데이트된 문제 엔티티
     */
    @Transactional
    public ProblemDto updateProblem(Long id, ProblemDto newProblemDto) {
        log.info("문제 수정 시작 - 문제 ID: {}", id);
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ProblemNotFoundException("수정할 문제를 찾을 수 없습니다."));

        problem.update(newProblemDto);

        Problem modifiedProblem = problemRepository.save(problem);
        log.info("문제 수정 완료 - 문제 ID: {}", id);

        return problemConverter.toDto(modifiedProblem);
    }

    /**
     * 문제 삭제하는 메소드
     *
     * @param id 삭제할 문제의 ID
     */
    public void deleteProblem(Long id) {
        try {
            log.info("문제 삭제 시작 - 문제 ID: {}", id);
            if (!problemRepository.existsById(id)) {
                throw new ProblemNotFoundException("삭제할 문제를 찾을 수 없습니다.");
            }

            problemRepository.deleteById(id);
            log.info("문제 삭제 완료 - 문제 ID: {}", id);

        } catch (ProblemNotFoundException e) {
            log.error("문제 삭제 실패 - 문제를 찾을 수 없음 (ID: {})", id);
            throw e;

        } catch (Exception e) {
            log.error("문제 삭제 중 오류 발생 - 문제 ID: {}, 오류: {}", id, e.getMessage());
            throw new RuntimeException("문제 삭제 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 주어진 카테고리의 문제들을 페이지네이션하여 반환하는 메소드
     *
     * @param problemCategory 조회할 문제의 카테고리
     * @param pageable        페이지네이션 정보를 포함하는 Pageable 객체
     * @return 해당 카테고리에 속하는 문제들의 페이지
     */
    public Page<Problem> getProblemsByCategory(ProblemCategory problemCategory, Pageable pageable) {
        log.info("카테고리별 문제 조회 시작 - 카테고리: {}", problemCategory);
        Page<Problem> problems = problemRepository.findByProblemCategory(problemCategory, pageable);

        log.info("카테고리별 문제 조회 완료 - 카테고리: {}, 조회된 문제 수: {}",
                problemCategory, problems.getTotalElements());
        return problems;
    }
}

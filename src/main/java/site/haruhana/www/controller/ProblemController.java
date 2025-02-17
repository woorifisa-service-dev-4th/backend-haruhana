package site.haruhana.www.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import site.haruhana.www.service.ProblemService;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.entity.ProblemCategory;
import site.haruhana.www.exception.ProblemNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public BaseResponse<Page<ProblemDto>> getAllProblems(Pageable pageable) {
        Page<ProblemDto> problems = problemService.getAllProblems(pageable);
        if (problems.isEmpty()) {
            return BaseResponse.onSuccess("조회된 문제가 없습니다", problems);
        } else {
            return BaseResponse.onSuccess("문제 목록 조회에 성공했습니다", problems);
        }
    }

    @GetMapping("/{problemId}")
    public BaseResponse<ProblemDto> getProblemById(@PathVariable Long problemId) {
        try {
            ProblemDto problem = problemService.getProblemById(problemId);
            return BaseResponse.onSuccess("문제 조회에 성공했습니다", problem);
        } catch (ProblemNotFoundException e) {
            return BaseResponse.onNotFound("문제를 찾을 수 없습니다");
        }
    }

    @PostMapping
    public BaseResponse<ProblemDto> createProblem(@RequestBody ProblemDto problemDto) {
        try {
            ProblemDto savedProblem = problemService.createProblem(problemDto);
            return BaseResponse.onCreate("문제가 성공적으로 생성되었습니다", savedProblem);
        } catch (Exception e) {
            return BaseResponse.onBadRequest("문제 생성에 실패했습니다: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public BaseResponse<ProblemDto> updateProblem(@PathVariable Long id, @RequestBody ProblemDto problemDto) {
        try {
            ProblemDto updatedProblem = problemService.updateProblem(id, problemDto);
            return BaseResponse.onSuccess("문제가 성공적으로 수정되었습니다", updatedProblem);
        } catch (ProblemNotFoundException e) {
            return BaseResponse.onNotFound("수정할 문제를 찾을 수 없습니다");
        }
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteProblem(@PathVariable Long id) {
        try {
            problemService.deleteProblem(id);
            return BaseResponse.onSuccess("문제가 성공적으로 삭제되었습니다", null);
        } catch (ProblemNotFoundException e) {
            return BaseResponse.onNotFound("삭제할 문제를 찾을 수 없습니다");
        }
    }

    @GetMapping("/category")
    public BaseResponse<List<Problem>> getProblemsByCategory(
            @RequestParam("category") ProblemCategory category,
            @PageableDefault Pageable pageable) {
        Page<Problem> page = problemService.getProblemsByCategory(category, pageable);
        List<Problem> problems = page.getContent();

        if (problems.isEmpty()) {
            return BaseResponse.onSuccess(
                    String.format("%s 카테고리에 문제가 없습니다", category),
                    problems
            );
        }

        return BaseResponse.onSuccess(
                String.format("%s 카테고리 문제 조회 성공 (총 %d개)",
                        category, page.getTotalElements()),
                problems
        );
    }
}
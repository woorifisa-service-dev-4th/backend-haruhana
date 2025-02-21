package site.haruhana.www.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.service.ProblemService;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<BaseResponse<Page<ProblemDto>>> getProblemList(Pageable pageable) {
        Page<ProblemDto> data = problemService.getAllProblems(pageable);
        BaseResponse<Page<ProblemDto>> response = BaseResponse.onSuccess("문제 목록 조회에 성공했습니다", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
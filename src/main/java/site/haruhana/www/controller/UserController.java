package site.haruhana.www.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.haruhana.www.dto.MonthlyUserSolveHistoryDTO;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/solve-history")
    public ResponseEntity<BaseResponse<MonthlyUserSolveHistoryDTO>> getUserSolveHistory(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        MonthlyUserSolveHistoryDTO data = userService.getMonthlyUserSolveHistory(userId, year, month);
        BaseResponse<MonthlyUserSolveHistoryDTO> response = BaseResponse.onSuccess("사용자의 문제 풀이 기록 조회에 성공했습니다.", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package site.haruhana.www.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.haruhana.www.dto.*;
import site.haruhana.www.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDTO) {
        LoginResponseDto data = userService.login(requestDTO);
        BaseResponse<LoginResponseDto> response = BaseResponse.onSuccess("로그인에 성공하였습니다.", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<UserDto>> signUp(@Valid @RequestBody SignUpRequestDto requestDTO) {
        UserDto data = userService.signUp(requestDTO);
        BaseResponse<UserDto> response = BaseResponse.onSuccess("회원가입이 완료되었습니다.", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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

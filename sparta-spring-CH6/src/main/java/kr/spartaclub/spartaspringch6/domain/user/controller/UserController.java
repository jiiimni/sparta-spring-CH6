package kr.spartaclub.spartaspringch6.domain.user.controller;

import jakarta.validation.Valid;
import kr.spartaclub.spartaspringch6.domain.user.dto.ChargeRequestDto;
import kr.spartaclub.spartaspringch6.domain.user.dto.ChargeResponseDto;
import kr.spartaclub.spartaspringch6.domain.user.service.UserService;
import kr.spartaclub.spartaspringch6.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}/point")
    public ApiResponse<ChargeResponseDto> charge(
            @PathVariable Long userId,
            @RequestBody @Valid ChargeRequestDto request) {
        return ApiResponse.success(userService.charge(userId, request));
    }
}
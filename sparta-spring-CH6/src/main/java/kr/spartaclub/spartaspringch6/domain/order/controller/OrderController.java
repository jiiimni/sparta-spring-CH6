package kr.spartaclub.spartaspringch6.domain.order.controller;

import jakarta.validation.Valid;
import kr.spartaclub.spartaspringch6.domain.order.dto.OrderRequestDto;
import kr.spartaclub.spartaspringch6.domain.order.dto.OrderResponseDto;
import kr.spartaclub.spartaspringch6.domain.order.service.OrderService;
import kr.spartaclub.spartaspringch6.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponseDto> order(@RequestBody @Valid OrderRequestDto request) {
        return ApiResponse.success(orderService.order(request));
    }
}
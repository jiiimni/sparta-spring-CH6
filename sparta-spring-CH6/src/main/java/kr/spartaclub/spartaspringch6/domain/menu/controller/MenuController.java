package kr.spartaclub.spartaspringch6.domain.menu.controller;

import kr.spartaclub.spartaspringch6.domain.menu.dto.MenuResponseDto;
import kr.spartaclub.spartaspringch6.domain.menu.dto.PopularMenuResponseDto;
import kr.spartaclub.spartaspringch6.domain.menu.service.MenuService;
import kr.spartaclub.spartaspringch6.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ApiResponse<List<MenuResponseDto>> getMenus() {
        return ApiResponse.success(menuService.getMenus());
    }

    @GetMapping("/popular")
    public ApiResponse<List<PopularMenuResponseDto>> getPopularMenus() {
        return ApiResponse.success(menuService.getPopularMenus());
    }
}
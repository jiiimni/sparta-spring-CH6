package kr.spartaclub.spartaspringch6.domain.menu.dto;

import lombok.Getter;

@Getter
public class PopularMenuResponseDto {
    private Long menuId;
    private String menuName;
    private Long orderCount;

    public PopularMenuResponseDto(Long menuId, String menuName, Long orderCount) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.orderCount = orderCount;
    }
}
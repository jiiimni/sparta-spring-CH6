package kr.spartaclub.spartaspringch6.domain.menu.dto;

import kr.spartaclub.spartaspringch6.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;

    private MenuResponseDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static MenuResponseDto of(Menu menu) {
        return new MenuResponseDto(menu.getId(), menu.getName(), menu.getPrice());
    }
}
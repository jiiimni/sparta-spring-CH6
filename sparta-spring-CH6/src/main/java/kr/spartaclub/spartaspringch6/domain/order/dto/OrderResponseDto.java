package kr.spartaclub.spartaspringch6.domain.order.dto;

import kr.spartaclub.spartaspringch6.domain.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private Long menuId;
    private String menuName;
    private int amount;

    private OrderResponseDto(Long orderId, Long userId, Long menuId, String menuName, int amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.amount = amount;
    }

    public static OrderResponseDto of(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getMenu().getId(),
                order.getMenu().getName(),
                order.getAmount()
        );
    }
}
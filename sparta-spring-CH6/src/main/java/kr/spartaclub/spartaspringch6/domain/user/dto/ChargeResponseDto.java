package kr.spartaclub.spartaspringch6.domain.user.dto;

import kr.spartaclub.spartaspringch6.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ChargeResponseDto {
    private Long userId;
    private int point;

    private ChargeResponseDto(Long userId, int point) {
        this.userId = userId;
        this.point = point;
    }

    public static ChargeResponseDto of(User user) {
        return new ChargeResponseDto(user.getId(), user.getPoint());
    }
}
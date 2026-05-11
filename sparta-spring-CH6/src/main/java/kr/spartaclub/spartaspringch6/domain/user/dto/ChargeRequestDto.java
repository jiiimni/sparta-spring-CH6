package kr.spartaclub.spartaspringch6.domain.user.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChargeRequestDto {

    @Min(value = 1, message = "충전 금액은 1원 이상이어야 합니다.")
    private int amount;

    // 테스트 코드에서 직접 객체 생성할 때 사용
    public ChargeRequestDto(int amount) {
        this.amount = amount;
    }
}
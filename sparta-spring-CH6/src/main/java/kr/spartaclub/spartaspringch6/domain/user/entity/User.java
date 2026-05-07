package kr.spartaclub.spartaspringch6.domain.user.entity;

import jakarta.persistence.*;
        import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int point;

    // 포인트 충전
    public void charge(int amount) {
        this.point += amount;
    }

    // 포인트 차감 (잔액 부족 시 예외)
    public void use(int amount) {
        if (this.point < amount) {
            throw new kr.spartaclub.spartaspringch6.global.exception.CustomException(
                    kr.spartaclub.spartaspringch6.global.exception.ErrorCode.INSUFFICIENT_POINT
            );
        }
        this.point -= amount;
    }
}
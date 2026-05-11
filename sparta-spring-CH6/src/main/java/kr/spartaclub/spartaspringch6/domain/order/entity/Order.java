package kr.spartaclub.spartaspringch6.domain.order.entity;

import jakarta.persistence.*;
import kr.spartaclub.spartaspringch6.domain.menu.entity.Menu;
import kr.spartaclub.spartaspringch6.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    // 주문 시점의 가격을 저장 - 나중에 메뉴 가격이 바뀌어도 주문 금액은 유지됨
    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Order of(User user, Menu menu) {
        Order order = new Order();
        order.user = user;
        order.menu = menu;
        order.amount = menu.getPrice();
        order.createdAt = LocalDateTime.now();
        return order;
    }
}
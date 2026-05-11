package kr.spartaclub.spartaspringch6.domain.order.repository;

import kr.spartaclub.spartaspringch6.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 최근 7일간 메뉴별 주문 횟수 집계 - 인기 메뉴 조회에 사용
    @Query("SELECT o.menu.id, o.menu.name, COUNT(o) as orderCount " +
            "FROM Order o " +
            "WHERE o.createdAt >= :since " +
            "GROUP BY o.menu.id, o.menu.name " +
            "ORDER BY orderCount DESC")
    List<Object[]> findTopMenusSince(@Param("since") LocalDateTime since);
}
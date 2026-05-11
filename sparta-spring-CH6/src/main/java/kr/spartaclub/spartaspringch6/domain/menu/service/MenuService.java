package kr.spartaclub.spartaspringch6.domain.menu.service;

import kr.spartaclub.spartaspringch6.domain.menu.dto.MenuResponseDto;
import kr.spartaclub.spartaspringch6.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.spartaclub.spartaspringch6.domain.order.repository.OrderRepository;
import kr.spartaclub.spartaspringch6.domain.menu.dto.PopularMenuResponseDto;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 - dirty checking 생략으로 성능 최적화
    public List<MenuResponseDto> getMenus() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::of)
                .toList();
    }

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<PopularMenuResponseDto> getPopularMenus() {
        // 현재 시각 기준 7일 전부터 지금까지의 주문 집계
        LocalDateTime since = LocalDateTime.now().minusDays(7);

        // 상위 3개만 잘라서 반환
        return orderRepository.findTopMenusSince(since)
                .stream()
                .limit(3)
                .map(row -> new PopularMenuResponseDto(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .toList();
    }
}
package kr.spartaclub.spartaspringch6.domain.order.service;

import kr.spartaclub.spartaspringch6.domain.menu.entity.Menu;
import kr.spartaclub.spartaspringch6.domain.menu.repository.MenuRepository;
import kr.spartaclub.spartaspringch6.domain.order.dto.OrderRequestDto;
import kr.spartaclub.spartaspringch6.domain.order.dto.OrderResponseDto;
import kr.spartaclub.spartaspringch6.domain.order.entity.Order;
import kr.spartaclub.spartaspringch6.domain.order.repository.OrderRepository;
import kr.spartaclub.spartaspringch6.domain.user.entity.User;
import kr.spartaclub.spartaspringch6.domain.user.repository.UserRepository;
import kr.spartaclub.spartaspringch6.global.exception.CustomException;
import kr.spartaclub.spartaspringch6.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto order(OrderRequestDto request) {
        // 비관적 락으로 유저 조회
        // 동시에 같은 유저가 여러 주문을 시도해도 포인트 차감이 순차적으로 처리됨
        User user = userRepository.findByIdWithLock(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 포인트 차감 - 잔액 부족 시 User.use() 내부에서 예외 발생
        user.use(menu.getPrice());

        // 주문 생성 및 저장
        Order order = Order.of(user, menu);
        orderRepository.save(order);

        // 데이터 수집 플랫폼으로 주문 정보 전송 (Mock)
        sendToDataPlatform(user.getId(), menu.getId(), order.getAmount());

        return OrderResponseDto.of(order);
    }

    // 실제 환경에서는 외부 API 호출 or 메시지 큐로 전송
    // 여기서는 Mock으로 로그만 출력
    private void sendToDataPlatform(Long userId, Long menuId, int amount) {
        log.info("[DataPlatform] 주문 전송 - userId: {}, menuId: {}, amount: {}", userId, menuId, amount);
    }
}
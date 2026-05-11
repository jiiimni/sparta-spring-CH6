package kr.spartaclub.spartaspringch6;

import kr.spartaclub.spartaspringch6.domain.user.entity.User;
import kr.spartaclub.spartaspringch6.domain.user.repository.UserRepository;
import kr.spartaclub.spartaspringch6.domain.user.service.UserService;
import kr.spartaclub.spartaspringch6.domain.user.dto.ChargeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceConcurrencyTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        // 매 테스트마다 포인트 0인 새 유저 생성
        User user = new User();
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    @DisplayName("동시에 10번 1000원 충전 시 최종 잔액은 10000원이어야 한다")
    void concurrentChargeTest() throws InterruptedException {
        int threadCount = 10;
        int chargeAmount = 1000;

        // 여러 스레드가 동시에 작업을 실행할 수 있게 해주는 스레드 풀
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // 모든 스레드가 동시에 시작하고 끝날 때까지 기다리게 해주는 동기화 도구
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    ChargeRequestDto request = new ChargeRequestDto(chargeAmount);
                    userService.charge(userId, request);
                } finally {
                    latch.countDown(); // 스레드 작업 완료 신호
                }
            });
        }

        latch.await(); // 모든 스레드 완료까지 대기
        executorService.shutdown();

        User user = userRepository.findById(userId).orElseThrow();

        // 비관적 락이 없으면 동시 요청으로 인해 10000원보다 적게 충전될 수 있음
        assertThat(user.getPoint()).isEqualTo(threadCount * chargeAmount);
    }
}
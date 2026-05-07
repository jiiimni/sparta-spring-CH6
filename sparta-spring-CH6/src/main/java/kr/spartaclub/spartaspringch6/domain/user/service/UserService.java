package kr.spartaclub.spartaspringch6.domain.user.service;

import kr.spartaclub.spartaspringch6.domain.user.dto.ChargeRequestDto;
import kr.spartaclub.spartaspringch6.domain.user.dto.ChargeResponseDto;
import kr.spartaclub.spartaspringch6.domain.user.repository.UserRepository;
import kr.spartaclub.spartaspringch6.global.exception.CustomException;
import kr.spartaclub.spartaspringch6.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ChargeResponseDto charge(Long userId, ChargeRequestDto request) {
        // 비관적 락(PESSIMISTIC_WRITE)으로 조회
        // 동일 유저에 대한 동시 충전 요청이 와도 하나씩 순차 처리됨
        // 락 없이 처리하면 잔액이 덮어씌워지는 race condition 발생 가능
        var user = userRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.charge(request.getAmount());

        // @Transactional + JPA dirty checking으로 별도 save() 호출 없이 자동 반영
        return ChargeResponseDto.of(user);
    }
}
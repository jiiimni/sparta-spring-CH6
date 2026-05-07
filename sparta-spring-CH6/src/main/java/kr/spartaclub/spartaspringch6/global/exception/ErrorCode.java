package kr.spartaclub.spartaspringch6.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INSUFFICIENT_POINT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액은 1원 이상이어야 합니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
package kr.spartaclub.spartaspringch6.domain.menu.service;

import kr.spartaclub.spartaspringch6.domain.menu.dto.MenuResponseDto;
import kr.spartaclub.spartaspringch6.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
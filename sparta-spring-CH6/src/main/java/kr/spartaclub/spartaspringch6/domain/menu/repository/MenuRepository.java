package kr.spartaclub.spartaspringch6.domain.menu.repository;

import kr.spartaclub.spartaspringch6.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
package az.developia.BalanceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import az.developia.BalanceApp.entity.BalanceEntity;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    BalanceEntity findByUserId(Long userId);
}

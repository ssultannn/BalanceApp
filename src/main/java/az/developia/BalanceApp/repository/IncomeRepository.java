package az.developia.BalanceApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import az.developia.BalanceApp.entity.IncomeEntity;
import az.developia.BalanceApp.response.IncomeResponse;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
	List<IncomeEntity> findByUserId(@Param("userId") Long userId);

	List<IncomeEntity> findByUserIdOrderByDateAsc(Long userId); // Сортировка по возрастанию даты

	List<IncomeEntity> findByUserIdOrderByDateDesc(Long userId); // Сортировка по убыванию даты
}

package az.developia.BalanceApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.BalanceApp.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {

	List<ExpenseEntity> findByUserId(Long userId);

	   List<ExpenseEntity> findByUserIdOrderByAmount(Long userId);
	    List<ExpenseEntity> findByUserIdOrderByDate(Long userId);
}

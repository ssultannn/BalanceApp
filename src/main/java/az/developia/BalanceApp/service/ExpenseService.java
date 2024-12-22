package az.developia.BalanceApp.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.repository.ExpenseRepository;
import az.developia.BalanceApp.response.ExpenseResponse;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public ExpenseEntity addExpense(ExpenseEntity expense) {
		return expenseRepository.save(expense);
	}

	public List<ExpenseResponse> getExpensesByUser(Long userId) {
		return expenseRepository.findByUserId(userId).stream().map(this::toExpenseResponse)
				.collect(Collectors.toList());
	}

	private ExpenseResponse toExpenseResponse(ExpenseEntity entity) {
		ExpenseResponse response = new ExpenseResponse();
		response.setCategory(entity.getCategory());
		response.setAmount(entity.getAmount());
		response.setUserId(entity.getUserId());
		response.setDate(entity.getDate()); // Устанавливаем дату
		return response;
	}

	public List<ExpenseEntity> getSortedExpensesByUser(Long userId, String sortBy) {
	    List<ExpenseEntity> expenses = expenseRepository.findByUserId(userId);

	    if (sortBy == null || sortBy.isEmpty()) {
	        sortBy = "date"; // Значение по умолчанию
	    }

	    if ("date".equalsIgnoreCase(sortBy)) {
	        expenses.sort(Comparator.comparing(ExpenseEntity::getDate));
	    } else if ("amount".equalsIgnoreCase(sortBy)) {
	        expenses.sort(Comparator.comparing(ExpenseEntity::getAmount));
	    }

	    return expenses;
	}
	public List<ExpenseEntity> getFilteredExpensesByUser(Long userId, String sortBy, LocalDate startDate, LocalDate endDate) {
	    List<ExpenseEntity> expenses = expenseRepository.findByUserId(userId);

	    // Фильтрация по датам, если они указаны
	    if (startDate != null) {
	        expenses = expenses.stream()
	                .filter(expense -> !expense.getDate().isBefore(startDate))
	                .collect(Collectors.toList());
	    }

	    if (endDate != null) {
	        expenses = expenses.stream()
	                .filter(expense -> !expense.getDate().isAfter(endDate))
	                .collect(Collectors.toList());
	    }

	    // Сортировка по выбранному полю
	    if ("date".equalsIgnoreCase(sortBy)) {
	        expenses.sort(Comparator.comparing(ExpenseEntity::getDate));
	    } else if ("amount".equalsIgnoreCase(sortBy)) {
	        expenses.sort(Comparator.comparing(ExpenseEntity::getAmount));
	    }

	    return expenses;
	}

}
package az.developia.BalanceApp.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.exception.MyException;
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
		response.setDate(entity.getDate());
		return response;
	}

	public List<ExpenseEntity> getSortedExpensesByUser(Long userId, String sortBy) {
		List<ExpenseEntity> expenses = expenseRepository.findByUserId(userId);

		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "date"; // Default value
		}

		if ("date".equalsIgnoreCase(sortBy)) {
			expenses.sort(Comparator.comparing(ExpenseEntity::getDate));
		} else if ("amount".equalsIgnoreCase(sortBy)) {
			expenses.sort(Comparator.comparing(ExpenseEntity::getAmount));
		}

		return expenses;
	}

	public List<ExpenseEntity> getFilteredExpensesByUser(Long userId, String sortBy, LocalDate startDate,
			LocalDate endDate) {
		List<ExpenseEntity> expenses = expenseRepository.findByUserId(userId);

		// Filtering by dates if provided
		if (startDate != null) {
			expenses = expenses.stream().filter(expense -> !expense.getDate().isBefore(startDate))
					.collect(Collectors.toList());
		}

		if (endDate != null) {
			expenses = expenses.stream().filter(expense -> !expense.getDate().isAfter(endDate))
					.collect(Collectors.toList());
		}

		// Sorting by selected field
		if ("date".equalsIgnoreCase(sortBy)) {
			expenses.sort(Comparator.comparing(ExpenseEntity::getDate));
		} else if ("amount".equalsIgnoreCase(sortBy)) {
			expenses.sort(Comparator.comparing(ExpenseEntity::getAmount));
		}

		return expenses;
	}

	// Update expense
	public ExpenseEntity updateExpense(Long expenseId, ExpenseEntity updatedExpense) {
		ExpenseEntity expense = expenseRepository.findById(expenseId)
				.orElseThrow(() -> new MyException("Expense not found"));
		expense.setCategory(updatedExpense.getCategory());
		expense.setAmount(updatedExpense.getAmount());
		expense.setDate(updatedExpense.getDate() != null ? updatedExpense.getDate() : LocalDate.now());
		return expenseRepository.save(expense);
	}

	// Delete expense
	public void deleteExpense(Long expenseId) {
		ExpenseEntity expense = expenseRepository.findById(expenseId)
				.orElseThrow(() -> new MyException("Expense not found"));
		expenseRepository.delete(expense);
	}
}

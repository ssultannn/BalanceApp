package az.developia.BalanceApp.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.entity.ExpensePlanEntity;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.repository.ExpenseRepository;
import az.developia.BalanceApp.repository.UserRepository;
import az.developia.BalanceApp.response.ExpenseResponse;
import az.developia.BalanceApp.response.ExpenseStatus;

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

 

    public ExpenseEntity updateExpense(Long expenseId, ExpenseEntity updatedExpense, Long userId) {
        ExpenseEntity expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new MyException("Expense not found"));
        if (!expense.getUserId().equals(userId)) {
            throw new MyException("You can only update your own expenses");
        }
        expense.setCategory(updatedExpense.getCategory());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDate(updatedExpense.getDate() != null ? updatedExpense.getDate() : LocalDate.now());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId, Long userId) {
        ExpenseEntity expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new MyException("Expense not found"));
        if (!expense.getUserId().equals(userId)) {
            throw new MyException("You can only delete your own expenses");
        }
        expenseRepository.delete(expense);
    }
	public ExpenseStatus checkExpenseStatus(ExpensePlanEntity plan) {
	    long daysBetween = ChronoUnit.DAYS.between(plan.getStartDate(), plan.getEndDate());
	    double dailyAmount = plan.getTotalAmount() / daysBetween;

	    long daysPassed = ChronoUnit.DAYS.between(plan.getStartDate(), LocalDate.now());
	    double expectedExpenses = dailyAmount * daysPassed;

	    double difference = plan.getActualExpenses() - expectedExpenses;
	    ExpenseStatus status = new ExpenseStatus();

	    if (plan.getActualExpenses() > expectedExpenses) {
	        status.setStatus("negative");
	        status.setMessage("Your expenses exceed the plan, you need to reduce your spending.");
	        status.setDifference(difference);
	    } else if (plan.getActualExpenses() < expectedExpenses) {
	        status.setStatus("positive");
	        status.setMessage("Your expenses are below the plan, you're on the right track!");
	        status.setDifference(difference);
	    } else {
	        status.setStatus("neutral");
	        status.setMessage("Your expenses are in line with the plan.");
	        status.setDifference(difference);
	    }

	    return status;
	}

	public List<ExpenseEntity> findByUserId(Long userId) {
		// TODO Auto-generated method stub
		return expenseRepository.findByUserId(userId);
	}

}

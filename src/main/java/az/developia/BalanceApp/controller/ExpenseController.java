package az.developia.BalanceApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.entity.ExpensePlanEntity;
import az.developia.BalanceApp.enums.SortBy;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.response.ExpenseResponse;
import az.developia.BalanceApp.response.ExpenseStatus;
import az.developia.BalanceApp.service.ExpenseService;
@RestController
@RequestMapping("/expense")
public class ExpenseController {

	 @Autowired
	    private ExpenseService expenseService;

	    @PostMapping
	    public ResponseEntity<ExpenseEntity> addExpense(@RequestBody ExpenseEntity expense) {
	        if (expense.getDate() == null) {
	            expense.setDate(LocalDate.now()); // Устанавливаем текущую дату, если она не передана
	        }
	        return ResponseEntity.ok(expenseService.addExpense(expense));
	    }

	    @GetMapping("/{userId}")
	    public ResponseEntity<List<ExpenseResponse>> getExpenses(@PathVariable Long userId) {
	        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
	    }
	    @GetMapping("/{userId}/sorted")
	    public ResponseEntity<List<ExpenseEntity>> getSortedExpenses(
	            @PathVariable Long userId,
	            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy) {
	        return ResponseEntity.ok(expenseService.getSortedExpensesByUser(userId, sortBy.getValue()));
	    }
	    @GetMapping("/{userId}/filtered")
	    public ResponseEntity<List<ExpenseEntity>> getFilteredExpenses(
	            @PathVariable Long userId,
	            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy,
	            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
	        
	        return ResponseEntity.ok(expenseService.getFilteredExpensesByUser(userId, sortBy.getValue(), startDate, endDate));
	    }

    // Update expense by ID
    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseEntity updatedExpense) {
        ExpenseEntity expense = expenseService.updateExpense(expenseId, updatedExpense);
        return ResponseEntity.ok(expense);
    }

    // Delete expense by ID
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/check-expenses")
    public ExpenseStatus checkExpenses(@RequestBody ExpensePlanEntity plan) {
        return expenseService.checkExpenseStatus(plan);
    }
}


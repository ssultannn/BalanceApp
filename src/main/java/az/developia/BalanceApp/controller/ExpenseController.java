package az.developia.BalanceApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.enums.SortBy;
import az.developia.BalanceApp.service.ExpenseService;
import az.developia.BalanceApp.security.UserContext;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserContext userContext;

    @PostMapping
    public ResponseEntity<ExpenseEntity> addExpense(@RequestBody ExpenseEntity expense) {
        Long userId = userContext.getCurrentUserId();
        expense.setUserId(userId); // Set the authenticated user's ID
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseEntity>> getExpenses() {
        Long userId = userContext.getCurrentUserId();
        List<ExpenseEntity> expenses = expenseService.findByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<ExpenseEntity>> getSortedExpenses(
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy) {
        Long userId = userContext.getCurrentUserId();
        return ResponseEntity.ok(expenseService.getSortedExpensesByUser(userId, sortBy.getValue()));
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<ExpenseEntity>> getFilteredExpenses(
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long userId = userContext.getCurrentUserId();
        return ResponseEntity.ok(expenseService.getFilteredExpensesByUser(userId, sortBy.getValue(), startDate, endDate));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseEntity updatedExpense) {
        Long userId = userContext.getCurrentUserId();
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, updatedExpense, userId));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        Long userId = userContext.getCurrentUserId();
        expenseService.deleteExpense(expenseId, userId);
        return ResponseEntity.noContent().build();
    }
}

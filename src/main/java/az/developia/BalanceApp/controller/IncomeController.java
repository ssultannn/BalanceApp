package az.developia.BalanceApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import az.developia.BalanceApp.entity.IncomeEntity;
import az.developia.BalanceApp.enums.SortBy;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.security.UserContext;
import az.developia.BalanceApp.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    
    @Autowired
    private UserContext userContext;

    @PostMapping
    public ResponseEntity<IncomeEntity> addIncome(@RequestBody IncomeEntity income) {
        Long userId = userContext.getCurrentUserId();  // Извлекаем userId из контекста
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }

        if (income.getAmount() == null) {
            throw new MyException("Income amount is required!");
        }

        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }

        income.setUserId(userId);  // Устанавливаем userId из контекста, без необходимости передавать его в запросе
        return ResponseEntity.ok(incomeService.addIncome(income));
    }
    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeEntity>> getIncomes() {
        Long userId = userContext.getCurrentUserId(); // Извлекаем userId из контекста
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }

        return ResponseEntity.ok(incomeService.getIncomesByUser(userId));
    }




    @GetMapping("/sorted")
    public ResponseEntity<List<IncomeEntity>> getSortedIncomes(
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy) {
        Long userId = userContext.getCurrentUserId();
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }
        return ResponseEntity.ok(incomeService.getSortedIncomesByUser(userId, sortBy.getValue()));
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<IncomeEntity>> getFilteredIncomes(
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long userId = userContext.getCurrentUserId();
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }
        return ResponseEntity.ok(incomeService.getFilteredIncomesByUser(userId, sortBy.getValue(), startDate, endDate));
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeEntity> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeEntity updatedIncome) {
        Long userId = userContext.getCurrentUserId();
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }

        updatedIncome.setUserId(userId);  // Устанавливаем userId из контекста
        IncomeEntity income = incomeService.updateIncome(incomeId, updatedIncome);
        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId) {
        Long userId = userContext.getCurrentUserId();
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }

        incomeService.deleteIncome(incomeId, userId);
        return ResponseEntity.noContent().build();
    }
}


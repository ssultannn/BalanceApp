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

import az.developia.BalanceApp.entity.IncomeEntity;
import az.developia.BalanceApp.enums.SortBy;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.response.IncomeResponse;
import az.developia.BalanceApp.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeEntity> addIncome(@RequestBody IncomeEntity income) {
        if (income.getAmount() == null) {
            throw new MyException("Income amount is required!");
        }
        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }
        return ResponseEntity.ok(incomeService.addIncome(income));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<IncomeEntity>> getIncomes(@PathVariable Long userId) {
        if (userId == null) {
            throw new MyException("User ID is required!");
        }
        return ResponseEntity.ok(incomeService.getIncomesByUser(userId));
    }

    @GetMapping("/{userId}/sorted")
    public ResponseEntity<List<IncomeEntity>> getSortedIncomes(
            @PathVariable Long userId,
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy) {
        if (userId == null) {
            throw new MyException("User ID is required!");
        }
        return ResponseEntity.ok(incomeService.getSortedIncomesByUser(userId, sortBy.getValue()));
    }

    @GetMapping("/{userId}/filtered")
    public ResponseEntity<List<IncomeEntity>> getFilteredIncomes(
            @PathVariable Long userId,
            @RequestParam(value = "sortBy", defaultValue = "DATE") SortBy sortBy,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (userId == null) {
            throw new MyException("User ID is required!");
        }
        return ResponseEntity.ok(incomeService.getFilteredIncomesByUser(userId, sortBy.getValue(), startDate, endDate));
    }

    // Update income by ID
    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeEntity> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeEntity updatedIncome) {
        IncomeEntity income = incomeService.updateIncome(incomeId, updatedIncome);
        return ResponseEntity.ok(income);
    }

    // Delete income by ID
    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId) {
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.noContent().build();
    }
}


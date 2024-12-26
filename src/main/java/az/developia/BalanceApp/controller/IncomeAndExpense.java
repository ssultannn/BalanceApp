package az.developia.BalanceApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.entity.IncomeEntity;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.response.BalanceListsResponse;
import az.developia.BalanceApp.security.UserContext;
import az.developia.BalanceApp.service.ExpenseService;
import az.developia.BalanceApp.service.IncomeService;
@RestController
@RequestMapping("/IncomeAndExpense")
public class IncomeAndExpense {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserContext userContext;  

    @GetMapping("/filtered")
    public ResponseEntity<Object> getBalanceBetweenDates(
            @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Long userId = userContext.getCurrentUserId();  // Извлекаем userId из контекста
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }

        // Fetching filtered income and expense data
        List<IncomeEntity> incomes = incomeService.getFilteredIncomesByUser(userId, "date", startDate, endDate);
        List<ExpenseEntity> expenses = expenseService.getFilteredExpensesByUser(userId, "date", startDate, endDate);

        // Creating a response object
        BalanceListsResponse response = new BalanceListsResponse();
        response.setIncomes(incomes);
        response.setExpenses(expenses);

        return ResponseEntity.ok(response);
    }
}

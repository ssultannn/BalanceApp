package az.developia.BalanceApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.BalanceEntity;
import az.developia.BalanceApp.repository.BalanceRepository;
import az.developia.BalanceApp.response.BalanceResponse;

@Service
public class BalanceService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BalanceRepository balanceRepository;

    public BalanceResponse calculateAndSaveBalance(Long userId) {
        // Подсчет общей суммы доходов и расходов
        double totalIncome = incomeService.getIncomesByUser(userId)
                .stream()
                .mapToDouble(income -> income.getAmount())
                .sum();

        double totalExpense = expenseService.getExpensesByUser(userId)
                .stream()
                .mapToDouble(expense -> expense.getAmount())
                .sum();

        double balance = totalIncome - totalExpense;

        // Сохранение или обновление баланса
        BalanceEntity balanceEntity = balanceRepository.findByUserId(userId);
        if (balanceEntity == null) {
            balanceEntity = new BalanceEntity();
            balanceEntity.setUserId(userId);
        }

        balanceEntity.setTotalIncome(totalIncome);
        balanceEntity.setTotalExpense(totalExpense);
        balanceEntity.setBalance(balance);
        balanceRepository.save(balanceEntity);

        // Возврат результата
        return new BalanceResponse(totalIncome, totalExpense, balance, "Balance calculated and saved successfully!");
    }

    public BalanceResponse getBalanceByUserId(Long userId) {
        BalanceEntity balanceEntity = balanceRepository.findByUserId(userId);

        if (balanceEntity == null) {
            return new BalanceResponse(0.0, 0.0, 0.0, "No balance found for this user.");
        }

        return new BalanceResponse(
            balanceEntity.getTotalIncome(),
            balanceEntity.getTotalExpense(),
            balanceEntity.getBalance(),
            "Balance retrieved successfully."
        );
    }
}
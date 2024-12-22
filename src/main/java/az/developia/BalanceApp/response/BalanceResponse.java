package az.developia.BalanceApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
    private String message;
}

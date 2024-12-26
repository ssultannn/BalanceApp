package az.developia.BalanceApp.response;

import java.util.List;

import az.developia.BalanceApp.entity.ExpenseEntity;
import az.developia.BalanceApp.entity.IncomeEntity;
import lombok.Data;

@Data
public class BalanceListsResponse {
	private List<IncomeEntity> incomes;
	private List<ExpenseEntity> expenses;
}

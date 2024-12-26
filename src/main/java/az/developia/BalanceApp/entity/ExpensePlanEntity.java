package az.developia.BalanceApp.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExpensePlanEntity {
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalAmount;
	private double actualExpenses;
}

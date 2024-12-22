package az.developia.BalanceApp.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExpenseResponse {

	private String category; 

	private Double amount; 

	private Long userId; 
	
	 private LocalDate date; // Новое поле
}


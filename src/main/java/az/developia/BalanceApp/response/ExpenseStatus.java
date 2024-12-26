package az.developia.BalanceApp.response;

import lombok.Data;

@Data
public class ExpenseStatus {
	  private String status;
	    private String message;
	    private double difference;
}

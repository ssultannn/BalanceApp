package az.developia.BalanceApp.response;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Data;
@Data
public class IncomeResponse {

    
    private Double amount;

    private String description;

    private Long userId; 
    
    private LocalDate date; // Новое поле
}

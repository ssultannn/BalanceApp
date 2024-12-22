package az.developia.BalanceApp.entity;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "expense")
public class ExpenseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Schema(hidden = true)
	private Long id;

	private String category; // Категория расхода

	private Double amount; // Сумма расхода

	private Long userId; // ID пользователя, который добавил этот расход
	 
	private LocalDate date; // Новое поле
}

package az.developia.BalanceApp.entity;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "expense")
public class ExpenseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
@Schema(hidden = true)
	private Long id;
@NotBlank(message = "Category cannot be empty")
	private String category;

@NotNull(message = "Amount cannot be null")
	private Double amount;
@Column(name = "user_id", nullable = false)
@Schema(hidden = true)  // Скрываем userId
@NotNull(message = "User ID cannot be null")
	private Long userId;

	@NotNull(message = "Date cannot be null")
	private LocalDate date;
}

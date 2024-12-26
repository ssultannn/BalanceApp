package az.developia.BalanceApp.entity;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Table(name = "income")
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)  // Скрываем это поле в Swagger UI
    private Long id;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Column(name = "user_id", nullable = false)
    @Schema(hidden = true)  // Скрываем userId
    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Это поле не будет показываться в Swagger

    private LocalDate date;
}


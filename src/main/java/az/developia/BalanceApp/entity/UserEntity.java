package az.developia.BalanceApp.entity;




import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
@Schema(hidden = true)
	private Long id;


@NotBlank(message = "Username cannot be empty")
    @Column(unique = true, nullable = false)
    private String username;

@NotBlank(message = "Password cannot be empty")
    @Column(nullable = false)
    private String password;

//@Email(message = "Email should be valid")
    private String mail;

}

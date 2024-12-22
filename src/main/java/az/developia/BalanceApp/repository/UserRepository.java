package az.developia.BalanceApp.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.BalanceApp.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);

	  UserEntity findByMail(String mail);
}


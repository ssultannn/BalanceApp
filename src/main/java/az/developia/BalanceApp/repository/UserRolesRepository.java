package az.developia.BalanceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.BalanceApp.entity.UserRolesEntity;

public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Long> {
    UserRolesEntity findByUsername(String username);
}

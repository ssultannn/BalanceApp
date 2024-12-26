package az.developia.BalanceApp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import az.developia.BalanceApp.repository.UserRepository;
import az.developia.BalanceApp.entity.UserEntity;

@Component
public class UserContext {

    private final UserRepository userRepository;

    public UserContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Возвращает ID текущего аутентифицированного пользователя.
     * Используется имя пользователя для нахождения ID.
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Невозможно определить ID пользователя");
        }

        String username = authentication.getName(); // Получаем имя пользователя

        // Находим пользователя по имени и возвращаем его ID
        UserEntity user = userRepository.findByUsername(username);
              
        
        return user.getId(); // Получаем ID пользователя
    }
}

package az.developia.BalanceApp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);
        // Query for retrieving user information
        jdbcDao.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        jdbcDao.setAuthoritiesByUsernameQuery("SELECT username, role FROM user_roles WHERE username = ?");
        return jdbcDao;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        // Use plaintext passwords (no encoding)
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/login", "/register").permitAll()  // Allow access to login and register
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("USER")  // Swagger UI and docs for ROLE_USER only
            .requestMatchers(HttpMethod.GET, "/users/**").hasRole("USER")
            .requestMatchers(HttpMethod.POST, "/income/**").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .httpBasic()  // Enable Basic Authentication
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session
            .and()
            .headers().frameOptions().disable()  // Allow frames for H2 console
            .and()
            .build();
    }
}

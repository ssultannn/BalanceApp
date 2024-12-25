//package az.developia.BalanceApp.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.mysql.cj.protocol.AuthenticationProvider;
//
//import javax.sql.DataSource;
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        jdbcUserDetailsManager.setDataSource(dataSource);
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        return authProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeRequests()
//                    // Разрешаем доступ к Swagger UI и его ресурсам
//                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
//                    // Разрешаем доступ к H2 Console
//                    .requestMatchers("/h2-console/**").permitAll()
//                    // Разрешаем доступ к другим публичным путям
//                    .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
//                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                    // Требуем аутентификацию для остальных путей
//                    .anyRequest().authenticated()
//                .and()
//                .httpBasic() // Используем базовую аутентификацию
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .headers().frameOptions().disable() // Разрешаем использование фреймов для H2 Console
//                .and()
//                .build();
//    }
//    protected void configure(HttpSecurity http) throws Exception {
//        // Отключаем безопасность для Swagger
//        http.csrf().disable()
//            .authorizeRequests()
//            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Разрешаем доступ к Swagger
//            .anyRequest().authenticated(); // Требуем аутентификацию для других маршрутов
//    }
//}
//

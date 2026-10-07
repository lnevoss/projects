// package io.github.lnevoss.juice_shop.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;


// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 // public
//                 .requestMatchers("/error").permitAll()
//                 .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
//                 .requestMatchers("/api/auth/**").permitAll()
//                 // admin only
//                 .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
//                 .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
//                 .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
//                 // logged-in users
//                 .requestMatchers("/api/orders/**").authenticated()
//                 // everything else
//                 .anyRequest().denyAll()
//             )
//             .httpBasic(Customizer.withDefaults());

//         return http.build();
//     }

//     @Bean
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     UserDetailsService testUsers(PasswordEncoder encoder) {
//         return new InMemoryUserDetailsManager(
//             User.withUsername("customer")
//                 .password(encoder.encode("customer123"))
//                 .roles("CUSTOMER")
//                 .build(),
//             User.withUsername("admin")
//                 .password(encoder.encode("admin123"))
//                 .roles("ADMIN")
//                 .build()
//         );
//     }
// }
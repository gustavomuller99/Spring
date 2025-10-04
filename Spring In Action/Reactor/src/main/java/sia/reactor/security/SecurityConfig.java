package sia.reactor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/design", "/orders").hasAuthority("USER")
                        .anyExchange().permitAll())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public ReactiveUserDetailsService userDetailsService(
//            UserRepository userRepo) {
//        return new ReactiveUserDetailsService() {
//            @Override
//            public Mono<UserDetails> findByUsername(String username) {
//                return userRepo.findByUsername(username)
//                        .map(user -> {
//                            return user.toUserDetails();
//                        });
//            }
//        };
//    }
}

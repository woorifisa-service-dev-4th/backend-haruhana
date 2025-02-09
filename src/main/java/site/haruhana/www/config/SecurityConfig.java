package site.haruhana.www.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import site.haruhana.www.oauth.CustomOAuth2UserService;
import site.haruhana.www.oauth.handler.OAuth2LoginFailureHandler;
import site.haruhana.www.oauth.handler.OAuth2LoginSuccessHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${spring.security.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
            OAuth2LoginFailureHandler oAuth2LoginFailureHandler
    ) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable) // Rest API 사용으로 CSRF 비활성화.
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of(allowedOrigins)); // CORS 허용 도메인 설정.
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // CORS 허용 메소드 설정.
                        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // CORS 허용 헤더 설정.
                        return config;
                    };

                    // CORS 설정 적용.
                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().permitAll() // FixMe: 현재 모든 요청을 허용하고 있지만, 추후 인증 관련 API가 개발되면 수정 필요.
                )
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .userInfoEndpoint(
                                userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService)
                        )
                        .failureHandler(oAuth2LoginFailureHandler)
                        .successHandler(oAuth2LoginSuccessHandler)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 사용으로 세션 비활성화.
                )
                .build();

    }

}

package live.smoothing.front.config;

import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security 설정 클래스
 *
 * @author 우혜승

 */
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthAdaptor authAdaptor;

    /**
     * SecurityFilterChain 빈 생성 메서드
     * SecurityFilterChain 을 생성한다.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception SecurityFilterChain 생성 실패 시 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .addFilterAt(new CustomLoginFilter(authenticationManager(null)), UsernamePasswordAuthenticationFilter.class)
                .securityContext().securityContextRepository(new CustomSecurityContextRepository()).and()
//                .addFilter(new CustomAuthenticationFilter(authenticationManager(null)))
                .authorizeRequests()
//                .antMatchers("/assets/**").permitAll()
//                .antMatchers("/error").permitAll()
//                .antMatchers("/static/**").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout().addLogoutHandler(new CustomLogoutHandler(authAdaptor))
                .permitAll();

        return http.build();
    }

    /**
     * WebSecurityCustomizer 빈 생성 메서드
     * Security Filter 적용을 제외할 URL 을 설정한다.
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/assets/**", "/error", "/static/**");
    }

    /**
     * AuthenticationManager 빈 생성 메서드
     * AuthenticationManager 를 생성한다.
     *
     * @param authenticationConfiguration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception AuthenticationManager 생성 실패 시 예외
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * AuthenticationProvider 빈 생성 메서드
     * CustomAuthenticationProvider 를 생성한다.
     *
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider(authAdaptor);
    }
}

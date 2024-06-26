package live.smoothing.front.config;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.auth.service.AuthService;
import live.smoothing.front.security.*;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * Security 설정 클래스
 *
 * @author 우혜승
 */
@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthAdapter authAdapter;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserApiAdapter userAdapter;
    private final UserService userService;
    private final AuthService authService;

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
                .cors().disable()
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
                .antMatchers("/oauth").permitAll()
                .antMatchers("/requestCertificationNumber").permitAll()
                .antMatchers("/verifyCertificationNumber").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/existUser").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(new CustomLogoutHandler(authAdapter));


        http.oauth2Login()
//                .userInfoEndpoint().userService(new CustomOAuth2Service())
//                .and()
                .successHandler(new CustomOAuth2AuthenticationSuccessHandler(userService,authService)).and().oauth2Login().loginPage("/login");
//
        http.oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(new CustomHttpSessionOAuth2AuthorizationRequestRepository());
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
    public AuthenticationProvider authenticationProvider() {

        return new CustomAuthenticationProvider(authAdapter, userAdapter);
    }

    @Bean
    public HttpSessionRequestCache httpSessionRequestCache() {

        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);
        return httpSessionRequestCache;
    }
}

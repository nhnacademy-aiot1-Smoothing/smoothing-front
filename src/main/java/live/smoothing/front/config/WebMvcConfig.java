package live.smoothing.front.config;

import live.smoothing.front.auth.adapter.AuthAdapter;
import live.smoothing.front.interceptor.ReissueJwtTokenInterceptor;
import live.smoothing.front.interceptor.TokenRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 구성을 위한 설정 클래스
 *
 * @author 박영준
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ObjectProvider<AuthAdapter> authAdaptorObjectProvider;

    /**
     * Jwt 토큰 재발급 인터셉터 빈 생성 메서드
     *
     * @return Jwt 토큰 재발급 인터셉터 객체
     */
    @Bean
    public ReissueJwtTokenInterceptor reissueJwtTokenInterceptor() {

        return new ReissueJwtTokenInterceptor(authAdaptorObjectProvider.getIfAvailable());
    }

    /**
     * Jwt 토큰 재발급 인터셉터 빈 생성 메서드
     *
     * @return Jwt 토큰 재발급 인터셉터 객체
     */
    @Bean
    public TokenRequestInterceptor tokenRequestInterceptor() {

        return new TokenRequestInterceptor();
    }

    /**
     * 인터셉터를 등록하는 메서드
     *
     * @param registry 인터셉터 등록 담당 객체
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(reissueJwtTokenInterceptor());
    }
}

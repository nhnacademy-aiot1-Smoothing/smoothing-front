package live.smoothing.front.config;

import live.smoothing.front.interceptor.TokenHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 구성을 위한 설정 클래스
 *
 * @author 박영준
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 인터셉터를 등록하는 메서드
     *
     * @param registry 인터셉터 등록 담당 객체
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenHandlerInterceptor());
    }
}

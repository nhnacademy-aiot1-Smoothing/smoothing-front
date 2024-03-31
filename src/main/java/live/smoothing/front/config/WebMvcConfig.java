//package live.smoothing.front.config;
//
//import live.smoothing.front.adapter.AuthAdaptor;
//import live.smoothing.front.interceptor.ReissueJwtTokenInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private final ObjectProvider<AuthAdaptor> authAdaptorObjectProvider;
//
//    @Bean
//    public ReissueJwtTokenInterceptor reissueJwtTokenInterceptor() {
//        return new ReissueJwtTokenInterceptor(authAdaptorObjectProvider.getIfAvailable());
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(reissueJwtTokenInterceptor());
//    }
//}

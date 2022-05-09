package de.hsrm.mi.web.projekt.configuration;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class SprachConfig implements WebMvcConfigurer {

    @Bean
    public SessionLocaleResolver localeResolver() {
            
        SessionLocaleResolver resolver = new SessionLocaleResolver();
            // Optional: Browser-Präferenz übersteuern
            //resolver.setDefaultLocale(Locale.GERMANY);
        
    return resolver;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("sprache");
    
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    
}

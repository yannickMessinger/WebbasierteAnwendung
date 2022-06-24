package de.hsrm.mi.web.projekt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration @EnableWebSecurity
public class ProjektSecurityConfig  extends WebSecurityConfigurerAdapter {

    //public static final Logger logger = LoggerFactory.getLogger(ProjektSecurityConfig.class);
    @Autowired
    private MyUserDetailService userDetailService;

    @Bean 
    PasswordEncoder passwordEncoder() { // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication_manager) throws Exception { //-> nutzt BuilderPattern
       

        PasswordEncoder pw_encoder = passwordEncoder(); // Injection "in sich selbst" geht leider nicht

        authentication_manager
        .userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder());
        
        /* 
        authentication_manager.inMemoryAuthentication() // "in memory"-Benutzerdatenbank anlegen
            .withUser("friedfert")
            .password(pw_encoder.encode("dingdong")) // Passwörter nicht im Klartext speichern
            .roles("ADMIN")
        .and() 
            .withUser("joghurta")
            .password(pw_encoder.encode("chefin"))
            .roles("ADMIN")
        .and()
            .withUser("bärticus")
            .password(pw_encoder.encode("yannicksohilfmirbeimbankdrücken"))
            .roles("PUMPER");
        
        */
            

        //logger.info("USER ANGELEGT SECURITY CONFIG");


        
    }
    
    
    @Override
            protected void configure(HttpSecurity http) throws Exception {
                
                
                http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/registrieren","/logout").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                //.antMatchers("/register", "/logout").permitAll()
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","USER")
                .antMatchers("/user*", "/user/*").authenticated()
                .anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                //.loginPage("/login") // falls eigenes Login-Formular
                .defaultSuccessUrl("/benutzerprofil")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // ist auch Default
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**");

                http.headers().frameOptions().disable();
    
        
    
            }
            
            
}
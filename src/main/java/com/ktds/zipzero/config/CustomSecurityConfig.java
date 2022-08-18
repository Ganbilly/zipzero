package com.ktds.zipzero.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ktds.zipzero.security.CustomUserDetailsService;
import com.ktds.zipzero.security.handler.Custom403Handler;
import com.ktds.zipzero.security.handler.CustomSocialLoginSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("------------configure-------------------");

        //커스텀 로그인 페이지
        http.formLogin().loginPage("/member/login");
        //CSRF 토큰 비활성화
        http.csrf().disable();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403
        http.authorizeRequests().antMatchers("/member/login").permitAll().anyRequest().authenticated();
        http.rememberMe().tokenValiditySeconds(60*60*24).userDetailsService(userDetailsService);
        http.formLogin().successHandler(authenticationSuccessHandler());
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/member/login").invalidateHttpSession(true);
        
        return http.build();
    }




    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }
    // @Bean
    // public PersistentTokenRepository persistentTokenRepository() {
    //     JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
    //     repo.setDataSource(dataSource);
    //     return repo;
    // }

}
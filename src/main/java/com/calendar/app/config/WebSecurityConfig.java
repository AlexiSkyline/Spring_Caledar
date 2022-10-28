package com.calendar.app.config;

import com.calendar.app.security.UserDetailsServiceImpl;
import com.calendar.app.security.jwt.AuthEntryPointJwt;
import com.calendar.app.security.jwt.AuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @AllArgsConstructor @EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( this.userDetailsService );
        authProvider.setPasswordEncoder( this.passwordEncoder() );

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint( this.unauthorizedHandler )
                .and()
                .sessionManagement().sessionCreationPolicy( STATELESS )
                .and()
                .authorizeRequests().antMatchers( "/api/auth/**", "/api/test/**" ).permitAll()
                .anyRequest().authenticated();

        httpSecurity.authenticationProvider( this.authenticationProvider() );
        httpSecurity.addFilterBefore( this.authTokenFilter(), UsernamePasswordAuthenticationFilter.class );

        return httpSecurity.build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename( "classpath:messages" );
        messageSource.setDefaultEncoding( "UTF-8" );

        return messageSource;
    }
}
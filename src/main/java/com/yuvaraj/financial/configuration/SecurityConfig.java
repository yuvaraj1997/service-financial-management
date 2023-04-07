package com.yuvaraj.financial.configuration;

import com.yuvaraj.financial.filters.CustomAuthenticationFilter;
import com.yuvaraj.financial.filters.CustomAuthorizationFilter;
import com.yuvaraj.financial.models.db.AuthorityEntity;
import com.yuvaraj.financial.services.SignInService;
import com.yuvaraj.security.services.JwtGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.yuvaraj.financial.helpers.Constants.LOGIN_PROCESSING_URL;
import static com.yuvaraj.financial.helpers.Constants.SESSION_TOKEN_GENERATION_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtGenerationService jwtGenerationService;
    private final SignInService signInService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), jwtGenerationService, signInService);
        customAuthenticationFilter.setFilterProcessesUrl(LOGIN_PROCESSING_URL);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(SESSION_TOKEN_GENERATION_URL).authenticated();
        http.authorizeRequests().antMatchers("/v1/signup/**", "/v1/forgot-password/**").permitAll();
        http.authorizeRequests().antMatchers("/v1/**").authenticated();
        http.authorizeRequests().antMatchers("/v1/m/**").hasAuthority(AuthorityEntity.Role.SUPER_ADMIN.getRole());
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(signInService, jwtGenerationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
//        configuration.setExposedHeaders(Arrays.asList("Authorization"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Bean
    PasswordEncoder passwordEncoder() {
        //TODO: Think to secure more
        return new BCryptPasswordEncoder();
    }
}

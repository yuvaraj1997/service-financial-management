package com.yuvaraj.financial.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financial.helpers.ResponseHelper;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.services.SignInService;
import com.yuvaraj.security.models.AuthSuccessfulResponse;
import com.yuvaraj.security.services.JwtGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.yuvaraj.financial.helpers.Constants.SESSION_TOKEN_GENERATION_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final SignInService signInService;
    private final JwtGenerationService jwtGenerationService;

    public CustomAuthorizationFilter(SignInService signInService, JwtGenerationService jwtGenerationService) {
        this.signInService = signInService;
        this.jwtGenerationService = jwtGenerationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        try {
            String authorization = request.getHeader(AUTHORIZATION);
            String userId = request.getParameter("userId");
            if (servletPath.equals(SESSION_TOKEN_GENERATION_URL)) {
                response.setContentType(APPLICATION_JSON_VALUE);
                signInService.validateRefreshToken(authorization, userId);
                AuthSuccessfulResponse authSuccessfulResponse = jwtGenerationService.generateSessionToken(userId, signInService.getEmailAddressFromToken(authorization));
                new ObjectMapper().writeValue(response.getOutputStream(), authSuccessfulResponse);
                return;
            } else if (null != authorization && !authorization.isEmpty()) {
                UserEntity userEntity = signInService.validateSessionToken(authorization);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        userEntity.getId(),
                        null,
                        userEntity.getAuthorities().stream().map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getRole())).collect(Collectors.toList())));
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Exception: Attempted to access authenticated url errorMessage={}, errorClass={}", e.getMessage(), e.getClass().getSimpleName());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            new ObjectMapper().writeValue(response.getOutputStream(), ResponseHelper.handleGeneralException(HttpStatus.FORBIDDEN.value(), null));
        }
    }
}

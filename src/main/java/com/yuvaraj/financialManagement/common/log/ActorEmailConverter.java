package com.yuvaraj.financialManagement.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.yuvaraj.financialManagement.services.SignInService;
import com.yuvaraj.financialManagement.services.impl.SignInServiceImpl;
import com.yuvaraj.security.services.impl.JwtManagerServiceImpl;
import com.yuvaraj.security.services.impl.TokenValidationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Yuvaraj
 */
@Slf4j
public class ActorEmailConverter extends ClassicConverter {

    SignInService signInService;

    public ActorEmailConverter() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.signInService = new SignInServiceImpl(null, null, new TokenValidationServiceImpl(), new JwtManagerServiceImpl());
    }

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String emailAddress = "-";
        if (null == this.signInService) {
            return emailAddress;
        }
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                String authorization = ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(AUTHORIZATION);

                if (null != authorization && !authorization.isEmpty()) {
                    emailAddress = this.signInService.getEmailAddressFromToken(authorization);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return emailAddress;
    }


}

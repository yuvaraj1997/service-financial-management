package com.yuvaraj.financial.models.inbuiltClass;

import com.yuvaraj.financial.models.signIn.SignInRequest;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private String userId;
    private SignInRequest signInRequest;
    private String ipAddress;
    private String deviceName;
    private String deviceType;
    private String deviceSubtype;

    public CustomUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public CustomUser(String userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public CustomUser(String userId, String ipAddress, String deviceName, String deviceType, String deviceSubtype, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.deviceSubtype = deviceSubtype;
    }

    public CustomUser(SignInRequest signInRequest, String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.signInRequest = signInRequest;
        this.userId = userId;
    }
}

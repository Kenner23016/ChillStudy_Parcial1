package com.libcode.crud.crud.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomAuthoritiesMapper extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Obtener los roles desde el token JWT (claim personalizado)
        List<String> roles = oidcUser.getClaimAsStringList("https://chillstudy.com/roles");

        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());

        if (roles != null) {
            for (String role : roles) {
                mappedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}

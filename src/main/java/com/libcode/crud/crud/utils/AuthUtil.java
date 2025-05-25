package com.libcode.crud.crud.utils;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Map;

public class AuthUtil {

    /**
     * Obtiene los roles personalizados desde el token del usuario autenticado.
     * Asegúrate que el namespace sea el mismo que usaste en tu Action de Auth0.
     */
    public static List<String> getRoles(OidcUser user) {
        Map<String, Object> claims = user.getClaims();
        String namespace = "https://chillstudy.com/roles"; // Usa el mismo namespace configurado en Auth0

        Object rolesClaim = claims.get(namespace);
        if (rolesClaim instanceof List<?> rolesList) {
            return rolesList.stream().map(Object::toString).toList();
        }

        return List.of(); // No hay roles
    }

    /**
     * Verifica si el usuario tiene un rol específico.
     */
    public static boolean hasRole(OidcUser user, String role) {
        return getRoles(user).contains(role);
    }
}

package com.libcode.crud.crud.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addRolesToModel(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            List<String> roles = user.getClaimAsStringList("https://chillstudy.com/roles");
            model.addAttribute("roles", roles);
        }
    }
}

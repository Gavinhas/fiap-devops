package br.com.fiap.mural.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

/**
 * Expõe flags para os templates sem usar o dialecto {@code sec:} do Thymeleaf.
 */
@ControllerAdvice
public class SecurityModelAdvice {

    private static final Logger log = LoggerFactory.getLogger(SecurityModelAdvice.class);

    @ModelAttribute("loggedIn")
    public boolean loggedIn(Authentication authentication) {
        try {
            return authentication != null
                    && !(authentication instanceof AnonymousAuthenticationToken)
                    && authentication.isAuthenticated();
        } catch (RuntimeException e) {
            log.warn("Erro ao avaliar sessão para template: {}", e.toString());
            return false;
        }
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(Authentication authentication) {
        return hasAuthority(authentication, "ROLE_ADMIN");
    }

    @ModelAttribute("isUserRole")
    public boolean isUserRole(Authentication authentication) {
        return hasAuthority(authentication, "ROLE_USER");
    }

    @ModelAttribute("currentUsername")
    public String currentUsername(Authentication authentication) {
        try {
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return "";
            }
            String name = authentication.getName();
            return name != null ? name : "";
        } catch (RuntimeException e) {
            log.warn("Erro ao ler nome do usuário: {}", e.toString());
            return "";
        }
    }

    private static boolean hasAuthority(Authentication authentication, String role) {
        if (authentication == null) {
            return false;
        }
        try {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities == null || authorities.isEmpty()) {
                return false;
            }
            for (GrantedAuthority a : authorities) {
                if (a == null || a.getAuthority() == null) {
                    continue;
                }
                if (role.equals(a.getAuthority())) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            log.warn("Erro ao ler roles: {}", e.toString());
            return false;
        }
    }
}

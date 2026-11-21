package com.eprocurement.admin.config;

import com.eprocurement.admin.security.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof com.eprocurement.admin.security.entity.User appUser) {
            return Optional.of(appUser.getUsername());
        }
        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            return Optional.of(springUser.getUsername());
        }
        User user = (User)authentication.getPrincipal();
        if (!authentication.isAuthenticated() || Objects.equals(authentication.getPrincipal(), "anonymousUser") || user == null) {

            return Optional.of("SYSTEM");
        }


        return Optional.of(user.getUserId());
    }
}

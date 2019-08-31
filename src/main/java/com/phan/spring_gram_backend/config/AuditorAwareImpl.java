package com.phan.spring_gram_backend.config;

import com.phan.spring_gram_backend.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAlias());
    }
}

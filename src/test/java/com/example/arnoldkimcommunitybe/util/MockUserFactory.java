package com.example.arnoldkimcommunitybe.util;

import com.example.arnoldkimcommunitybe.security.Session;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockUserFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Session session = new Session(annotation.id(), annotation.username());
        Authentication authToken = new UsernamePasswordAuthenticationToken(session, null, session.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}

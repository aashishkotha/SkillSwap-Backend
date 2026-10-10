package com.skillswap.security;

import com.skillswap.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwt;
    private final UserRepository users;

    public JwtFilter(JwtService j, UserRepository u) {
        jwt = j;
        users = u;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {

        String h = req.getHeader("Authorization");

        System.out.println("=================================");
        System.out.println("REQUEST: " + req.getMethod() + " " + req.getRequestURI());
        System.out.println("AUTH HEADER EXISTS: " + (h != null));

        if (h != null && h.startsWith("Bearer ")) {

            try {

                String token = h.substring(7);

                String email = jwt.extractEmail(token);

                System.out.println("JWT EMAIL: " + email);

                if (users.findByEmail(email).isPresent()) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    List.of()
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println("AUTHENTICATION SET: YES");

                } else {

                    System.out.println("USER NOT FOUND");

                }

            } catch (Exception e) {

                System.out.println("JWT ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println(
                "AUTHENTICATION AFTER FILTER: " +
                SecurityContextHolder.getContext().getAuthentication()
        );

        System.out.println("=================================");

        chain.doFilter(req, res);
    }
}
package com.Job_Portal_ATS.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        //  PUBLIC APIs skip
        if (path.equals("/api/auth/login") || 
        	    path.equals("/api/auth/register")) {

        	    filterChain.doFilter(request, response);
        	    return;
        	}


        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                Optional<User> optionalUser =
                        userRepository.findByEmail(email);

                if (optionalUser.isPresent()) {

                    User user = optionalUser.get();
                    
                    if (jwtUtil.validateToken(token, user.getEmail())) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(new SimpleGrantedAuthority(
                                            "ROLE_" + user.getRole().name()))
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}

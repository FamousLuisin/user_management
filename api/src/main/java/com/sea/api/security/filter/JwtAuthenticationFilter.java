package com.sea.api.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sea.api.model.User;
import com.sea.api.repository.UserRepository;
import com.sea.api.security.SecurityConfig;
import com.sea.api.security.jwt.JwtAuthenticationProvider;
import com.sea.api.security.user.CustomUserDetails;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        try {
            if (checkIfEndpointIsNotPublic(request)) {
                String token = recoveryToken(request);
                if (token != null) {
                    String subject = jwtProvider.getSubjectFromToken(token);
                    User user = userRepository.findByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("user not found"));

                    if (user.getRoles() == null || user.getRoles().isEmpty()) throw new RuntimeException("User has no roles assigned");

                    CustomUserDetails userDetails = new CustomUserDetails(user);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    sendError(response, HttpStatus.UNAUTHORIZED.value(), "Token not found");
                    return;
                }   
            }
        } catch (Exception ex) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            return;
        }
            
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        return Arrays.stream(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .noneMatch(pattern -> matcher.match(pattern, path));
    }
    
    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
    response.setStatus(status);
    response.setContentType("application/json");

    response.getWriter().write(
        "{ \"error\": \"" + message + "\" }"
    );
}
}

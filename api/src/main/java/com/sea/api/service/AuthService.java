package com.sea.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sea.api.dto.request.RegisterUserDTO;
import com.sea.api.dto.request.LoginUserDTO;
import com.sea.api.dto.response.JwtResponseDTO;
import com.sea.api.mapper.Mapper;
import com.sea.api.model.Role;
import com.sea.api.model.User;
import com.sea.api.model.Role.RoleType;
import com.sea.api.repository.RoleRepository;
import com.sea.api.repository.UserRepository;
import com.sea.api.security.jwt.JwtAuthenticationProvider;
import com.sea.api.security.user.CustomUserDetails;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Mapper mapper;

    public JwtResponseDTO login(LoginUserDTO request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new JwtResponseDTO(jwtProvider.generateToken(userDetails));
    }

    public JwtResponseDTO register(RegisterUserDTO request) {
        List<Role> roles = request.getRoles()
        .stream()
        .map(r -> "ROLE_" + r)
        .map(type -> RoleType.valueOf(type))
        .map(roleType -> roleRepository.findByName(roleType)
            .orElseThrow(() -> new RuntimeException("Role not found: " + roleType)))
        .collect(Collectors.toList());

        User user = mapper.parseObject(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new JwtResponseDTO(jwtProvider.generateToken(userDetails));
    }
}

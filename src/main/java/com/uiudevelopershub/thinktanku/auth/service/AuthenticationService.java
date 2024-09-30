package com.uiudevelopershub.thinktanku.auth.service;

import com.uiudevelopershub.thinktanku.auth.CustomUserDetails;
import com.uiudevelopershub.thinktanku.auth.dtos.requests.LoginRequestDTO;
import com.uiudevelopershub.thinktanku.auth.dtos.responses.LoginResponseDTO;
import com.uiudevelopershub.thinktanku.config.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.username(),
                        requestDTO.password()
                )
        );

        HashMap< String, Object > claims = new HashMap<>();

        CustomUserDetails user = ( CustomUserDetails ) authentication.getPrincipal();

        claims.put( "username", user.getUsername() );
        claims.put( "authorities", user.getAuthorities() );

        String jwtToken = jwtService.generateToken( claims, user );

        return new LoginResponseDTO(
                jwtToken,
                user.getUsername(),
                user.getEmail(),
                user
                        .getAuthorities()
                        .stream()
                        .map( GrantedAuthority::getAuthority )
                        .collect( Collectors.toSet() )
        );
    }
}
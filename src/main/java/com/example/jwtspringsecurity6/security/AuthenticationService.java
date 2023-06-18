package com.example.jwtspringsecurity6.security;

import com.example.jwtspringsecurity6.entity.Role;
import com.example.jwtspringsecurity6.entity.Token;
import com.example.jwtspringsecurity6.entity.TokenType;
import com.example.jwtspringsecurity6.entity.User;
import com.example.jwtspringsecurity6.exception.UserNameAlreadyExistException;
import com.example.jwtspringsecurity6.exception.UserNameNotFoundException;
import com.example.jwtspringsecurity6.repository.TokenRepository;
import com.example.jwtspringsecurity6.repository.UserRepository;
import com.example.jwtspringsecurity6.request.AuthenticationRequest;
import com.example.jwtspringsecurity6.request.RegisterRequest;
import com.example.jwtspringsecurity6.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.example.jwtspringsecurity6.constant.GeneralConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUserName(request.getUserName())){ //istenirse custom anotation da yazabilirim
            throw new UserNameAlreadyExistException(USERNAME_ALREADY_EXIST);
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new UserNameNotFoundException(USER_NOT_FOUND));
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validaUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validaUserToken.isEmpty()) {
            log.info(VALID_TOKEN_ERROR);
            return;
        }
        validaUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validaUserToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

}

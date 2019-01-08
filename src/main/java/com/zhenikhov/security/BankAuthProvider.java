package com.zhenikhov.security;

import com.zhenikhov.entity.BankClient;
import com.zhenikhov.repository.BankClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BankAuthProvider implements AuthenticationProvider {
    private final BankClientRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public BankAuthProvider(BankClientRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials()
                .toString();

        Optional<BankClient> optional = repository.findBankClientByLogin(username);
        if (optional.isPresent()) {
            if (!encoder.matches(password, optional.get().getPassword())) {
                throw new BadCredentialsException("User/Password is wrong");
            }

            return new UsernamePasswordAuthenticationToken(username, password);
        }

        throw new BadCredentialsException("User is not exists");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

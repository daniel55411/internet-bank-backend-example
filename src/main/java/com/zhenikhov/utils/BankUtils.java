package com.zhenikhov.utils;

import com.zhenikhov.entity.BankClient;
import com.zhenikhov.repository.BankClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Optional;


public class BankUtils {
    private BankUtils() {
    }

    public static Integer getBankClientId(BankClientRepository bankClientRepository,
                                    Principal principal)
            throws UserPrincipalNotFoundException {
        Optional<BankClient> optional = bankClientRepository.findBankClientByLogin(principal.getName());
        if (optional.isPresent()) {
            return optional.get().getId();
        }

        throw new UserPrincipalNotFoundException(null);
    }
}

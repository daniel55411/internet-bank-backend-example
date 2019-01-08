package com.zhenikhov.repository;

import com.zhenikhov.entity.BankClient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BankClientRepository extends CrudRepository<BankClient, String> {
    Optional<BankClient> findBankClientByLogin(String login);
}

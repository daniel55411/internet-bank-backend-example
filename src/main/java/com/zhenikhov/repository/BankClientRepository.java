package com.zhenikhov.repository;

import com.zhenikhov.entity.BankClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankClientRepository extends CrudRepository<BankClient, String> {
    Optional<BankClient> findBankClientByLogin(String login);
}

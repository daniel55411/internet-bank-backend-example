package com.zhenikhov.repository;

import com.zhenikhov.entity.BankClientInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankClientInfoRepository extends CrudRepository<BankClientInfo, Integer> {
    Optional<BankClientInfo> findBankClientInfoByBankClientId(Integer id);
}

package com.zhenikhov.repository;

import com.zhenikhov.entity.CardPayment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardPaymentRepository extends PaymentRepository<CardPayment>{
    Optional<CardPayment> findByIdAndBankClientId(Integer id, Integer bankClientId);
}

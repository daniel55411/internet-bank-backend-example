package com.zhenikhov.repository;

import com.zhenikhov.entity.CardPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPaymentRepository extends PaymentRepository<CardPayment>{
}

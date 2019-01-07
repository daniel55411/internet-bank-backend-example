package com.zhenikhov.repository;

import com.zhenikhov.entity.RequestedPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedPaymentRepository extends PaymentRepository<RequestedPayment> {
}

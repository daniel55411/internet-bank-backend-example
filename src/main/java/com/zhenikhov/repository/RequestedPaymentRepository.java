package com.zhenikhov.repository;

import com.zhenikhov.entity.RequestedPayment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestedPaymentRepository extends PagingAndSortingRepository<RequestedPayment, Integer> {
}

package com.zhenikhov.repository;

import com.zhenikhov.entity.RequestedPayment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface RequestedPaymentRepository extends
        PagingAndSortingRepository<RequestedPayment, Integer>,
        QueryByExampleExecutor<RequestedPayment> {
}

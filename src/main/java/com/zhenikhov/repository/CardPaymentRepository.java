package com.zhenikhov.repository;

import com.zhenikhov.entity.CardPayment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CardPaymentRepository extends
        PagingAndSortingRepository<CardPayment, Integer>,
        QueryByExampleExecutor<CardPayment> {
}

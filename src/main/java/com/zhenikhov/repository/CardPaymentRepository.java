package com.zhenikhov.repository;

import com.zhenikhov.entity.CardPayment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardPaymentRepository extends PagingAndSortingRepository<CardPayment, Integer> {
}

package com.zhenikhov.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface PaymentRepository<T> extends
        PagingAndSortingRepository<T, Integer>,
        QueryByExampleExecutor<T> {
}

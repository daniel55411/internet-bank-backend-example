package com.zhenikhov.controller;


import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("application.properties")
public class AdminControllerTest {
    @Autowired
    private CardPaymentRepository cardPaymentRepository;

    @Autowired
    private RequestedPaymentRepository requestedPaymentRepository;

    @Autowired
    private AdminController adminController;
}

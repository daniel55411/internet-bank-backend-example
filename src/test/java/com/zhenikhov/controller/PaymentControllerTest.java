package com.zhenikhov.controller;

import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentControllerTest {
    @Autowired
    private CardPaymentRepository cardPaymentRepository;

    @Autowired
    private RequestedPaymentRepository requestedPaymentRepository;

    @Autowired
    private PaymentController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads() {
        Assert.assertNotNull(controller);
    }

    @Test
    public void testSaveCardPayment() {

    }
}

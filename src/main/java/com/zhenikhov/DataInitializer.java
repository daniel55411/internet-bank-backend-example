package com.zhenikhov;

import com.zhenikhov.entity.*;
import com.zhenikhov.repository.BankClientInfoRepository;
import com.zhenikhov.repository.BankClientRepository;
import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataInitializer implements InitializingBean {
    private static final Random RANDOM = new Random();

    private final BankClientRepository bankClientRepository;
    private final BankClientInfoRepository bankClientInfoRepository;
    private final RequestedPaymentRepository requestedPaymentRepository;
    private final CardPaymentRepository cardPaymentRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public DataInitializer(BankClientInfoRepository bankClientInfoRepository,
                           BankClientRepository bankClientRepository,
                           CardPaymentRepository cardPaymentRepository,
                           RequestedPaymentRepository requestedPaymentRepository,
                           PasswordEncoder encoder) {
        this.bankClientInfoRepository = bankClientInfoRepository;
        this.bankClientRepository = bankClientRepository;
        this.cardPaymentRepository = cardPaymentRepository;
        this.requestedPaymentRepository = requestedPaymentRepository;
        this.encoder = encoder;
    }

    @Override
    public void afterPropertiesSet() {
        bankClientRepository.save(bankClient());
        bankClientInfoRepository.save(bankClientInfo());

        for (int i = 0; i < 3; i++) {
            cardPaymentRepository.save(cardPayment(i));
            requestedPaymentRepository.save(requestedPayment(i));
        }
    }

    private BankClient bankClient() {
        BankClient bankClient = new BankClient();
        bankClient.setId(1);
        bankClient.setLogin("user");
        bankClient.setPassword(encoder.encode("pass"));

        return bankClient;
    }

    private BankClientInfo bankClientInfo() {
        BankClientInfo bankClientInfo = new BankClientInfo();
        bankClientInfo.setBankClientId(1);
        bankClientInfo.setEmail("example@example.com");
        bankClientInfo.setFIO("Ivanov Ivan Ivanovich");
        bankClientInfo.setPhoneNumber("+7 (999) 999-99-99");
        bankClientInfo.setId(1);

        return bankClientInfo;
    }

    private RequestedPayment requestedPayment(Integer index) {
        RequestedPayment requestedPayment = new RequestedPayment();
        requestedPayment.setBankClientId(1);
        requestedPayment.setTransferAmount(RANDOM.nextInt(73000) + 1000);
        requestedPayment.setEmail("andre" + RandomStringUtils.randomAlphabetic(4) + "@mail.com");
        requestedPayment.setId(index);
        requestedPayment.setBic("1234" + RandomStringUtils.randomNumeric(5));
        requestedPayment.setAccountNumber("1234" + RandomStringUtils.randomNumeric(16));
        requestedPayment.setTin("000" + RandomStringUtils.randomNumeric(9));
        requestedPayment.setPhoneNumber("+7 (982) 723-37-58");
        requestedPayment.setVat(Vat.P10);

        return requestedPayment;
    }

    private CardPayment cardPayment(Integer index) {
        CardPayment cardPayment = new CardPayment();
        cardPayment.setTransferAmount(RANDOM.nextInt(73000) + 1000);
        cardPayment.setCardNumber("1234" + RandomStringUtils.randomNumeric(12));
        cardPayment.setBankClientId(1);
        cardPayment.setCommentary("commentary");
        cardPayment.setEmail("and" + RandomStringUtils.randomAlphabetic(5) + "@mail.com");
        cardPayment.setCardCvc("123");
        cardPayment.setCardDate("11/22");
        cardPayment.setId(index);

        return cardPayment;
    }
}

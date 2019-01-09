package com.zhenikhov;

import com.zhenikhov.entity.BankClient;
import com.zhenikhov.entity.BankClientInfo;
import com.zhenikhov.repository.BankClientInfoRepository;
import com.zhenikhov.repository.BankClientRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements InitializingBean {
    private final BankClientRepository bankClientRepository;
    private final BankClientInfoRepository bankClientInfoRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public DataInitializer(BankClientInfoRepository bankClientInfoRepository,
                           BankClientRepository bankClientRepository,
                           PasswordEncoder encoder) {
        this.bankClientInfoRepository = bankClientInfoRepository;
        this.bankClientRepository = bankClientRepository;
        this.encoder = encoder;
    }

    @Override
    public void afterPropertiesSet() {
        BankClient bankClient = new BankClient();
        bankClient.setId(1);
        bankClient.setLogin("user");
        bankClient.setPassword(encoder.encode("pass"));

        BankClientInfo bankClientInfo = new BankClientInfo();
        bankClientInfo.setBankClientId(1);
        bankClientInfo.setEmail("example@example.com");
        bankClientInfo.setFIO("Ivanov Ivan Ivanovich");
        bankClientInfo.setPhoneNumber("+7 (999) 999-99-99");
        bankClientInfo.setId(1);

        bankClientRepository.save(bankClient);
        bankClientInfoRepository.save(bankClientInfo);
    }
}

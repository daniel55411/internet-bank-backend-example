package com.zhenikhov.controller;

import com.itextpdf.text.DocumentException;
import com.zhenikhov.dto.InvoiceFactory;
import com.zhenikhov.dto.Result;
import com.zhenikhov.entity.BankClientInfo;
import com.zhenikhov.entity.BankClientPayment;
import com.zhenikhov.entity.CardPayment;
import com.zhenikhov.entity.RequestedPayment;
import com.zhenikhov.repository.BankClientInfoRepository;
import com.zhenikhov.repository.BankClientRepository;
import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import com.zhenikhov.utils.BankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class UserController {
    private CardPaymentRepository cardPaymentRepository;
    private RequestedPaymentRepository requestedPaymentRepository;
    private BankClientInfoRepository bankClientInfoRepository;
    private BankClientRepository bankClientRepository;

    @Autowired
    public UserController(CardPaymentRepository cardPaymentRepository,
                          RequestedPaymentRepository requestedPaymentRepository,
                          BankClientInfoRepository bankClientInfoRepository,
                          BankClientRepository bankClientRepository) {
        this.cardPaymentRepository = cardPaymentRepository;
        this.requestedPaymentRepository = requestedPaymentRepository;
        this.bankClientInfoRepository = bankClientInfoRepository;
        this.bankClientRepository = bankClientRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/info")
    public BankClientInfo bankClientInfo(@AuthenticationPrincipal Principal principal)
            throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        Optional<BankClientInfo> optionalInfo = bankClientInfoRepository
                .findBankClientInfoByBankClientId(bankClientId);
        if (optionalInfo.isPresent()) {
            return optionalInfo.get();
        }

        throw new UserPrincipalNotFoundException(principal.getName());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/pay")
    public Result payOperation(@AuthenticationPrincipal Principal principal,
                               @RequestBody CardPayment cardPayment)
            throws UserPrincipalNotFoundException {
        updatePayment(principal, cardPayment);
        cardPaymentRepository.save(cardPayment);

        return new Result(true, null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] getInvoiceOperation(@AuthenticationPrincipal Principal principal,
                               @RequestBody RequestedPayment payment)
            throws DocumentException, UserPrincipalNotFoundException {
        updatePayment(principal, payment);
        return InvoiceFactory.createInvoice(payment);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create-payment")
    public Result createPaymentOperation(@AuthenticationPrincipal Principal principal,
                                         @RequestBody RequestedPayment payment)
            throws UserPrincipalNotFoundException {
        updatePayment(principal, payment);
        requestedPaymentRepository.save(payment);

        return new Result(true, null);
    }

    private <T extends BankClientPayment> void updatePayment(Principal principal, T payment)
            throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        payment.setBankClientId(bankClientId);
    }
}

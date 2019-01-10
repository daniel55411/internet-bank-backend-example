package com.zhenikhov.controller;

import com.zhenikhov.dto.Result;
import com.zhenikhov.entity.BankClient;
import com.zhenikhov.entity.CardPayment;
import com.zhenikhov.entity.RequestedPayment;
import com.zhenikhov.repository.BankClientRepository;
import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.PaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import com.zhenikhov.utils.BankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final ExampleMatcher MATCHER = ExampleMatcher.matchingAll()
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withMatcher("id", ExampleMatcher.GenericPropertyMatcher::startsWith)
            .withMatcher("transferAmount", ExampleMatcher.GenericPropertyMatcher::startsWith);

    private CardPaymentRepository cardPaymentRepository;
    private RequestedPaymentRepository requestedPaymentRepository;
    private BankClientRepository bankClientRepository;

    @Autowired
    public AdminController(CardPaymentRepository cardPaymentRepository,
                           RequestedPaymentRepository requestedPaymentRepository,
                           BankClientRepository bankClientRepository) {
        this.cardPaymentRepository = cardPaymentRepository;
        this.requestedPaymentRepository = requestedPaymentRepository;
        this.bankClientRepository = bankClientRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-card-payments")
    public Iterable<CardPayment> getCardPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields,
            @AuthenticationPrincipal Principal principal) throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        CardPayment payment = new CardPayment();
        payment.setBankClientId(bankClientId);
        return getPayments(cardPaymentRepository, payment, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/get-card-payments")
    public Iterable<CardPayment> getCardPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields,
            @RequestBody CardPayment payment,
            @AuthenticationPrincipal Principal principal) throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        payment.setBankClientId(bankClientId);
        return getPayments(cardPaymentRepository, payment, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-requested-payments")
    public Iterable<RequestedPayment> getRequestedPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields,
            @AuthenticationPrincipal Principal principal) throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        RequestedPayment payment = new RequestedPayment();
        payment.setBankClientId(bankClientId);
        return getPayments(requestedPaymentRepository, payment, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/get-requested-payments")
    public Iterable<RequestedPayment> getRequestedPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields,
            @RequestBody RequestedPayment payment,
            @AuthenticationPrincipal Principal principal) throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        payment.setBankClientId(bankClientId);

        return getPayments(requestedPaymentRepository, payment, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/mark-unsafe-card-payment/{id}")
    public Result markUnsafeCardPayment(@PathVariable("id") Integer id,
                                        @AuthenticationPrincipal Principal principal)
            throws UserPrincipalNotFoundException {
        Integer bankClientId = BankUtils.getBankClientId(bankClientRepository, principal);
        Optional<CardPayment> optional = cardPaymentRepository.findByIdAndBankClientId(id, bankClientId);
        if (optional.isPresent()) {
            CardPayment payment = optional.get();
            payment.setUnsafe(true);
            cardPaymentRepository.save(payment);

            return new Result(true, null);
        }

        return new Result(false, "Card Payment with id " + id + " is not exist");
    }

    private <T> Iterable<T> getPayments(
            PaymentRepository<T> repository,
            T payment,
            Integer page,
            Integer size,
            String sortOrder,
            String[] sortFields) {
        Example<T> example = Example.of(payment, MATCHER);
        Sort sort = new Sort(direction(sortOrder), sortFields);

        if (size == -1) {
            return repository.findAll(example, sort);
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(example, pageable);
    }

    private Sort.Direction direction(String dir) {
        return dir.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
    }
}

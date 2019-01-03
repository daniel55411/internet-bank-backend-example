package com.zhenikhov.controller;

import com.zhenikhov.dto.Result;
import com.zhenikhov.entity.CardPayment;
import com.zhenikhov.entity.RequestedPayment;
import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.Card;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private CardPaymentRepository cardPaymentRepository;
    private RequestedPaymentRepository requestedPaymentRepository;

    @Autowired
    public AdminController(CardPaymentRepository cardPaymentRepository,
                           RequestedPaymentRepository requestedPaymentRepository) {
        this.cardPaymentRepository = cardPaymentRepository;
        this.requestedPaymentRepository = requestedPaymentRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-card-payments")
    public Iterable<CardPayment> getCardPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields) {
        return getPayments(cardPaymentRepository, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-requested-payments")
    public Iterable<RequestedPayment> getRequestedPayments(
            @RequestParam(name = "from", defaultValue = "0") Integer page,
            @RequestParam(name = "to", defaultValue = "-1") Integer size,
            @RequestParam(name = "sort-order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sort-field", defaultValue = "id") String[] sortFields) {
        return getPayments(requestedPaymentRepository, page, size, sortOrder, sortFields);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/mark-unsafe-card-payment/{id}")
    public Result markUnsafeCardPayment(@PathParam("id") Integer id) {
        Optional<CardPayment> optional = cardPaymentRepository.findById(id);
        if (optional.isPresent()) {
            CardPayment payment = optional.get();
            payment.setUnsafe(true);
            cardPaymentRepository.save(payment);

            return new Result(true, null);
        }

        return new Result(false, "Card Payment with id " + id + " is not exist");
    }

    private <T> Iterable<T> getPayments(
            PagingAndSortingRepository<T, Integer> repository,
            Integer page,
            Integer size,
            String sortOrder,
            String[] sortFields) {
        Sort sort = new Sort(direction(sortOrder), sortFields);

        if (size == -1) {
            return repository.findAll(sort);
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable);
    }

    private Sort.Direction direction(String dir) {
        return dir.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
    }
}

package com.zhenikhov.controller;

import com.itextpdf.text.DocumentException;
import com.zhenikhov.dto.InvoiceFactory;
import com.zhenikhov.dto.Result;
import com.zhenikhov.entity.CardPayment;
import com.zhenikhov.entity.RequestedPayment;
import com.zhenikhov.repository.CardPaymentRepository;
import com.zhenikhov.repository.RequestedPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class PaymentController {
    private CardPaymentRepository cardPaymentRepository;
    private RequestedPaymentRepository requestedPaymentRepository;

    @Autowired
    public PaymentController(CardPaymentRepository cardPaymentRepository,
                             RequestedPaymentRepository requestedPaymentRepository) {
        this.cardPaymentRepository = cardPaymentRepository;
        this.requestedPaymentRepository = requestedPaymentRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/pay")
    public Result payOperation(@RequestBody CardPayment cardPayment) {
        cardPaymentRepository.save(cardPayment);

        return new Result(true, null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getInvoiceOperation(@RequestBody RequestedPayment payment) throws DocumentException {
        return InvoiceFactory.createInvoice(payment);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create-payment")
    public Result createPaymentOperation(@RequestBody RequestedPayment payment) {
        requestedPaymentRepository.save(payment);

        return new Result(true, null);
    }
}

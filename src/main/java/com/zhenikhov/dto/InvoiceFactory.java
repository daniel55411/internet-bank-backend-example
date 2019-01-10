package com.zhenikhov.dto;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhenikhov.entity.RequestedPayment;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceFactory {
    private static final Font FONT = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    private static final Font UNDERLINE = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.UNDERLINE);
    private static final AtomicLong COUNTER = new AtomicLong(0);

    private InvoiceFactory() {

    }

    public static byte[] createInvoice(RequestedPayment payment) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, stream);
        document.open();
        addHeader(document);
        addTable(document, payment);
        document.close();

        return stream.toByteArray();
    }

    private static void addHeader(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Накладная № " + COUNTER.getAndIncrement(), UNDERLINE);

        document.add(title);
    }

    private static void addTable(Document document, RequestedPayment payment) throws DocumentException {
        PdfPTable table = new PdfPTable(2);

        table.addCell(new Phrase("Номер клиента", FONT));
        table.addCell(new Phrase(String.valueOf(payment.getBankClientId()), FONT));

        table.addCell(new Phrase("Номер счета", FONT));
        table.addCell(new Phrase(payment.getAccountNumber(), FONT));

        table.addCell(new Phrase("От Кого(ИНН)", FONT));
        table.addCell(new Phrase(payment.getTIN(), FONT));

        table.addCell(new Phrase("БИК", FONT));
        table.addCell(new Phrase(payment.getBIC(), FONT));

        table.addCell(new Phrase("За что", FONT));
        table.addCell(new Phrase(payment.getVAT().toString(), FONT));

        table.addCell(new Phrase("Сколько", FONT));
        table.addCell(new Phrase(payment.getTransferAmount().toString(), FONT));

        document.add(table);
    }
}

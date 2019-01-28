package com.zhenikhov.dto;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhenikhov.entity.RequestedPayment;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceFactory {
    private static final Font CYRILLIC_FONT = FontFactory.getFont("fonts/HelveticaBlack.ttf", "cp1251", BaseFont.EMBEDDED, 10);
    private static final Font BIG_CYRILLIC_FONT = FontFactory.getFont("fonts/HelveticaBlack.ttf", "cp1251", BaseFont.EMBEDDED, 16);
    private static final AtomicLong COUNTER = new AtomicLong(0);

    private InvoiceFactory() {
    }

    public static byte[] createInvoice(RequestedPayment payment) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, stream);
        document.open();
        addHeader(document);
        document.add( Chunk.NEWLINE );
        addTable(document, payment);
        document.close();

        return stream.toByteArray();
    }

    private static void addHeader(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Накладная № " + COUNTER.getAndIncrement(), BIG_CYRILLIC_FONT);

        document.add(title);
    }

    private static void addTable(Document document, RequestedPayment payment) throws DocumentException {
        PdfPTable table = new PdfPTable(2);

        table.addCell(new Phrase("Номер клиента", CYRILLIC_FONT));
        table.addCell(new Phrase(String.valueOf(payment.getBankClientId()), CYRILLIC_FONT));

        table.addCell(new Phrase("Номер счета", CYRILLIC_FONT));
        table.addCell(new Phrase(payment.getAccountNumber(), CYRILLIC_FONT));

        table.addCell(new Phrase("От Кого(ИНН)", CYRILLIC_FONT));
        table.addCell(new Phrase(payment.getTin(), CYRILLIC_FONT));

        table.addCell(new Phrase("БИК", CYRILLIC_FONT));
        table.addCell(new Phrase(payment.getBic(), CYRILLIC_FONT));

        table.addCell(new Phrase("За что", CYRILLIC_FONT));
        table.addCell(new Phrase(payment.getVat().toString(), CYRILLIC_FONT));

        table.addCell(new Phrase("Сколько", CYRILLIC_FONT));
        table.addCell(new Phrase(payment.getTransferAmount().toString(), CYRILLIC_FONT));

        document.add(table);
    }
}

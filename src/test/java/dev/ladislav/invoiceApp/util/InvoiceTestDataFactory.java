package dev.ladislav.invoiceApp.util;

import dev.ladislav.invoiceApp.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceTestDataFactory {
    public static Invoice createSampleInvoice() {
        Invoice sampleInvoice = new Invoice();
        sampleInvoice.setName("Test faktura");
        sampleInvoice.setAmount(new BigDecimal("1000.00"));
        sampleInvoice.setIssueDate(LocalDate.of(2025, 5, 13));
        sampleInvoice.setDueDate(LocalDate.of(2025, 6, 13));
        sampleInvoice.setStatus("Nezaplatena");

        return sampleInvoice;
    }
}

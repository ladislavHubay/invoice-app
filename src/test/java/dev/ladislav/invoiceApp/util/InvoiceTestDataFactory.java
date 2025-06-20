package dev.ladislav.invoiceApp.util;

import dev.ladislav.invoiceApp.model.Client;
import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.model.InvoiceItem;
import dev.ladislav.invoiceApp.model.Issuer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceTestDataFactory {
    public static Invoice createSampleInvoice() {
        Issuer issuer = new Issuer();
        Client client = new Client();
        Invoice sampleInvoice = new Invoice();
        InvoiceItem item = new InvoiceItem();

        issuer.setName("TEST 1");
        issuer.setStreet("Street issuer");
        issuer.setHouseNumber("10");
        issuer.setCity("City");
        issuer.setPostalCode("12345");
        issuer.setIco("12345678");
        issuer.setDic("1234567890");
        issuer.setPhone("+421915245899");
        issuer.setEmail("email@email.com");
        issuer.setBankAccount("kj1565487521369854712365");

        client.setName("TEST 2");
        client.setStreet("Street client");
        client.setHouseNumber("11");
        client.setCity("City");
        client.setPostalCode("12345");
        client.setIco("12345678");
        client.setDic("1234567890");

        sampleInvoice.setId(1L);
        sampleInvoice.setIssuer(issuer);
        sampleInvoice.setClient(client);

        sampleInvoice.setInvoiceNumber("2006202501");
        sampleInvoice.setVariableSymbol("2006202501");
        sampleInvoice.setIssueDate(LocalDate.of(2025, 5, 13));
        sampleInvoice.setDeliveryDate(LocalDate.of(2025, 5, 15));
        sampleInvoice.setDueDate(LocalDate.of(2025, 6, 15));

        item.setDescription("description item");
        item.setQuantity(new BigDecimal(2));
        item.setUnitPrice(new BigDecimal(10));

        sampleInvoice.addItem(item);

        return sampleInvoice;
    }
}

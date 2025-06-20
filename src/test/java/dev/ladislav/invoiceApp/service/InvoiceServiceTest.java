package dev.ladislav.invoiceApp.service;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")     // Spring boot aktivuje profil s priponou "test". V tomto pripade 'application-test.properties'
@DataJpaTest
@Import(InvoiceService.class)
public class InvoiceServiceTest {
    @Autowired
    private InvoiceService invoiceService;
    private Invoice sampleInvoice;

    @BeforeEach
    void setup(){
        sampleInvoice = InvoiceTestDataFactory.createSampleInvoiceWithoutID();
    }

    @Test
    void testCreateInvoiceSavesInvoiceAndReturnsId(){
        Invoice saved = invoiceService.createInvoice(sampleInvoice);
        assertNotNull(saved.getId());
        assertEquals("TEST 1", saved.getIssuer().getName());
    }

    @Test
    void testGetInvoiceByIdReturnsInvoice() {
        Invoice saved = invoiceService.createInvoice(sampleInvoice);
        Invoice found = invoiceService.getInvoiceById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getIssuer().getName(), found.getIssuer().getName());
    }

    @Test
    void testGetAllInvoicesReturnsList() {
        invoiceService.createInvoice(sampleInvoice);
        List<Invoice> invoices = invoiceService.getAllInvoices();
        assertEquals(1, invoices.size());
    }

    @Test
    void testDeleteInvoiceRemovesInvoice() {
        Invoice saved = invoiceService.createInvoice(sampleInvoice);
        invoiceService.deleteInvoice(saved.getId());
        assertNull(invoiceService.getInvoiceById(saved.getId()));
    }

    @Test
    void testGenerateInvoiceNumberReturnsUniqueNumber() {
        String number1 = invoiceService.generateInvoiceNumber(sampleInvoice.getIssueDate());
        invoiceService.createInvoice(sampleInvoice);
        String number2 = invoiceService.generateInvoiceNumber(sampleInvoice.getIssueDate());
        assertNotEquals(number1, number2);
        assertTrue(number1.startsWith("13052025"));
    }
}

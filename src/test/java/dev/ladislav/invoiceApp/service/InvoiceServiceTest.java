package dev.ladislav.invoiceApp.service;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")     // Spring boot aktivuje profil s priponou "test". V tomto pripade 'application-test.properties'
@DataJpaTest
@Import(InvoiceService.class)
public class InvoiceServiceTest {
    /*
    @Autowired
    private InvoiceService invoiceService;
    private final Invoice sampleInvoice = InvoiceTestDataFactory.createSampleInvoice();

    @Test
    void testCreateInvoiceValidDates(){
        Invoice saved = invoiceService.createInvoice(sampleInvoice);
        assertNotNull(saved.getId());
        assertEquals("Test faktura", saved.getName());
    }

    @Test
    void testCreateInvoiceInvalidDatesThrowsException() {
        sampleInvoice.setDueDate(LocalDate.of(2025, 5, 12));
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                invoiceService.createInvoice(sampleInvoice));
        assertEquals("Due date cannot be before issue date", exception.getMessage());
    }

    @Test
    void testGetInvoiceByIdReturnsInvoice() {
        Invoice saved = invoiceService.createInvoice(sampleInvoice);
        Invoice found = invoiceService.getInvoiceById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getName(), found.getName());
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
     */
}

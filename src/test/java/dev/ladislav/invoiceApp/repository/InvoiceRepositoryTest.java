package dev.ladislav.invoiceApp.repository;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;
    private final Invoice invoice = InvoiceTestDataFactory.createSampleInvoiceWithoutID();

    @Test
    void shouldSaveAndFindInvoiceById() {
        Invoice savedInvoice = invoiceRepository.save(invoice);

        assertThat(savedInvoice.getId()).isGreaterThan(0);
        assertThat(invoiceRepository.findById(savedInvoice.getId())).isPresent();
    }

    @Test
    void shouldDeleteInvoiceById(){
        Invoice savedInvoice = invoiceRepository.save(invoice);

        assertThat(savedInvoice.getId()).isGreaterThan(0);
        Long id = savedInvoice.getId();
        invoiceRepository.delete(savedInvoice);
        assertThat(invoiceRepository.findById(id)).isNotPresent();
    }

    @Test
    void shouldFindAllInvoices(){
        Invoice invoice;

        for (int i = 1; i <= 12; i++) {
            invoice = InvoiceTestDataFactory.createSampleInvoiceWithoutID();
            invoiceRepository.save(invoice);
        }
        assertThat(invoiceRepository.findAll().size()).isEqualTo(12);
    }

    @Test
    void shouldReturnEmptyOptionalWhenInvoiceNotFound(){
        Invoice savedInvoice = invoiceRepository.save(invoice);
        Long nonExistentId = savedInvoice.getId() + 1;
        Optional<Invoice> result = invoiceRepository.findById(nonExistentId);
        assertTrue(result.isEmpty());
    }
}

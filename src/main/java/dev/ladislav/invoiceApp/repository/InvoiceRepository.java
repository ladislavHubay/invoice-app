package dev.ladislav.invoiceApp.repository;

import dev.ladislav.invoiceApp.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * JpaRepository poskytuje vsetky CRUD operacie pre pracu s databazou bez toho aby bolo nutne manualne pisat
 * kod pre pracu s SQL scriptami. Je rozhranie Spring Data JPA. Pracuje s <Invoice, Long>, kde Invoice je
 * entita (trieda Invoice) a Long je primarny kluc, predstavuje ID faktury (konkretnej entity).
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    long countByIssueDate(LocalDate issueDate);
    boolean existsByInvoiceNumber(String invoiceNumber);
}

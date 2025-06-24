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
    /**
     * Vrati pocet faktur s rovnakym datumom vystavenia.
     * @param issueDate datum vystavenia.
     * @return vrati pocet faktur s rovnakym datumom vystavenia.
     */
    long countByIssueDate(LocalDate issueDate);

    /**
     * Metoda overi ci uz existuje faktura so zadanym cislom faktury.
     * @param invoiceNumber cislo faktury.
     * @return vrati TRUE ak faktura existuje, FALSE ak neexistuuje.
     */
    boolean existsByInvoiceNumber(String invoiceNumber);
}

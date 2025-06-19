package dev.ladislav.invoiceApp.service;

import dev.ladislav.invoiceApp.model.InvoiceItem;
import dev.ladislav.invoiceApp.repository.InvoiceRepository;
import dev.ladislav.invoiceApp.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service    // Spring boot tuto triedu zaregistruje ako servisnu komponentu (bean).
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired      // vytvara objekt (netreba manualne cez "new" vytvarat objekty).
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Metoda ulozi novu fakturu do databazy + VALIDACIA (skontroluje ci datum splatnosti je az po datume vystavenia).
     * @param invoice faktura
     * @return uklada fakturu
     */
    public Invoice createInvoice(Invoice invoice) {
        List<InvoiceItem> originalItems = new ArrayList<>(invoice.getItems());
        invoice.getItems().clear();

        for (InvoiceItem item : originalItems) {
            invoice.addItem(item);
        }

        return invoiceRepository.save(invoice);
    }

    /**
     * Metoda ziskava fakturu na zaklade ID.
     * @param id ID faktury
     * @return vrati fakturu s konkretnym ID
     */
    public Invoice getInvoiceById(long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    /**
     * Metoda ziska vsetky faktury.
     * @return vratcia vsetky najdene faktury
     */
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    /**
     * Metoda odstrane fakturu na zaklade ID.
     * @param id ID faktury
     */
    public void deleteInvoice(long id) {
        invoiceRepository.deleteById(id);
    }

    /**
     * Metoda na zaklade datumu vystavenia faktury a poctu v ten den vytvorenych faktur vygeneruje cislo faktury.
     * @param issueDate datum vystavenia.
     * @return Vrati cislo faktury.
     */
    public String generateInvoiceNumber(LocalDate issueDate) {
        String datePart = issueDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        long countForDate = invoiceRepository.countByIssueDate(issueDate);

        String invoiceNumber;
        int attempt = 1;

        do {
            String sequence = String.format("%02d", countForDate + attempt);
            invoiceNumber = datePart + sequence;
            attempt++;
        } while (invoiceRepository.existsByInvoiceNumber(invoiceNumber));

        return invoiceNumber;
    }
}

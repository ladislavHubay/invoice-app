package dev.ladislav.invoiceApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity     // Spring boot tuto triedu oznaci ako entitu JPA - mapuje sa na tabulku v databaze.
@Data       // Spring boot pre tuto triedu vygeneruje potrebne gettre a settre
public class Invoice {
    @Valid
    @ManyToOne(cascade = CascadeType.ALL)      // Tato entita moze byt sucastou viacerych faktur (invoices) a podla toho sa ma vytvorit stlpec v databaze. (cascade = CascadeType.ALL) zabezpeci ze sa entity ulozia spolu automaticky spolu s fakturou.
    @JoinColumn(name = "issuer_id", nullable = false)   // Vytvori v tabulke Invoice stlpec "issuer_id" (foreign key - cudzi kluc) v ktorej bude hodnota ID z Issuer. Nesmie byt null.
    private Issuer issuer;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)      // Tato entita moze byt sucastou viacerych faktur (invoices) a podla toho sa ma vytvorit stlpec v databaze. (cascade = CascadeType.ALL) zabezpeci ze sa entity ulozia spolu automaticky spolu s fakturou.
    @JoinColumn(name = "client_id", nullable = false)   // Vytvori v tabulke Invoice stlpec "client_id" (foreign key - cudzi kluc) v ktorej bude hodnota ID z Client. Nesmie byt null.
    private Client client;

    @Valid
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)   // tento zoznam (items) obsahuje polozky (invoiceItem) patriace k jednej fakture (invoice))
                                                                                        // mappedBy = "invoice" - vztah je mapovany cez invoice v triede InvoiceItem
                                                                                        // cascade = CascadeType.ALL - vsetko co sa stane so samotnou fakturou sa stane aj s jej polozkami(items) (napr: ulozenie fakturi - ulozia sa aj polozky)
                                                                                        // orphanRemoval = true - ak sa napr. vymaze polozka (ivoiceItem) zo zoznamu (items) tak sa vymaze aj z databazy
    @JsonManagedReference       // Zabranuje nekonecnej rekurzii pri prevode na JSON. Oznacuje vlastnika vztahu, stranu ktora sa ma serializovat.
    private List<InvoiceItem> items = new ArrayList<>();

    @Id     // Nastavi ako primarny kluc (s jedinecnou hodnotou) v databaze.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Zabezpeci automaticke generovanie jedinecnej hodnoty.
    private Long id;

    @NotBlank(message = "Invoice number is required")       // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String invoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")     // Nastavy presny format datumu aky sa ma pouzit pri zapise/citania JSON.
    @NotNull(message = "Issue date is required")    // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    private LocalDate issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")     // Nastavy presny format datumu aky sa ma pouzit pri zapise/citania JSON.
    @NotNull(message = "Due date is required")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")     // Nastavy presny format datumu aky sa ma pouzit pri zapise/citania JSON.
    @NotNull(message = "Delivery date is required")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    private LocalDate deliveryDate;

    @NotBlank(message = "Variable Symbol is required")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String variableSymbol;

    /**
     * Metoda spocita sucet vsetkych poloziek v jednej fakture.
     * @return vrati sucet poloziek.
     */
    public BigDecimal getTotalAmount() {
        if (items == null) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(InvoiceItem::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Metoda prida polozku do zoznamu poloziek ktore sa maju zobrazit na fakture.
     * @param item polozka.
     */
    public void addItem(InvoiceItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setInvoice(this);
    }

    /**
     * Metoda naformatuje datum do pozadovaneho formatu pre spravne zobrazenie na fakture.
     * @return vrati naformatovany datum.
     */
    public String getFormattedIssueDate() {
        return formatDate(issueDate);
    }

    /**
     * Metoda naformatuje datum do pozadovaneho formatu pre spravne zobrazenie na fakture.
     * @return vrati naformatovany datum.
     */
    public String getFormattedDeliveryDate() {
        return formatDate(deliveryDate);
    }

    /**
     * Metoda naformatuje datum do pozadovaneho formatu pre spravne zobrazenie na fakture.
     * @return vrati naformatovany datum.
     */
    public String getFormattedDueDate() {
        return formatDate(dueDate);
    }

    /**
     * Metoda naformatuje datum do pozadovaneho formatu.
     * @param date datum ktory sa ma naformatovat.
     * @return vrati naformatovany datum.
     */
    private String formatDate(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    /**
     * Metoda naformatuje vyslednu sumu (sucet vyslednej sumy vsetkych poloziek) na pozadovany format (na 2 desatinne miesta)
     * @return vrati naformatovanu sumu.
     */
    public String getFormattedTotalAmount() {
        BigDecimal total = getTotalAmount();
        return String.format("%.2f", total);
    }
}
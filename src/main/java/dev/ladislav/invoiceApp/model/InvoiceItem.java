package dev.ladislav.invoiceApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class InvoiceItem {
    @Id     // Nastavi ako primarny kluc (s jedinecnou hodnotou) v databaze.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Zabezpeci automaticke generovanie jedinecnej hodnoty.
    private Long id;

    @NotBlank(message = "Item description is required")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String description;

    @NotNull(message = "Quantity is required")    // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")    // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    @NotNull    // Tato hodnota nesmie byt Null.
    private BigDecimal totalPrice;

    @ManyToOne(optional = false)    // Entita InvoiceItem moze byt sucastou viacerych faktur (invoice) a podla toho sa ma vytvorit stlpec v databaze.
                                    // (optional = false) znamena ze entita InvoiceItem musi byt sucastou niektorej invoice.
    @JoinColumn(name = "invoice_id")    // Vytvori v tabulke InvoiceItem stlpec "invoice_id" (foreign key - cudzi kluc) v ktorej bude hodnota ID z Invoice.
    @JsonBackReference      // Zabranuje nekonecnej rekurzii pri prevode na JSON. Oznacuje stranu ktora sa neserializuje.
    private Invoice invoice;

    /**
     * Metoda nastavy mnozstvo a spusti metodu na vypocet celkovej sumy.
     * @param quantity mnozstvo
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        recalculateTotalPrice();
    }

    /**
     * Metoda nastavy cenu za jednotku a spusti metodu na vypocet celkovej sumy za polozku.
     * @param unitPrice cena za jednotku
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        recalculateTotalPrice();
    }

    /**
     * Metoda na vypocet celkovej sumy za polozku.
     */
    private void recalculateTotalPrice() {
        if (quantity != null && unitPrice != null) {
            this.totalPrice = quantity.multiply(unitPrice);
        } else {
            this.totalPrice = null;
        }
    }

    /**
     * Metoda naformatuje na dve desatinne miesta sumu jednej polozky zadanu uzivatelom.
     * @return vrati naformatovanu hodnotu ako retazec.
     */
    public String getFormattedUnitPrice() {
        return String.format("%.2f", unitPrice);
    }

    /**
     * Metoda naformatuje na dve desatinne miesta vyslednu sumu jednej polozky.
     * @return vrati naformatovanu hodnotu ako retazec.
     */
    public String getFormattedTotalPrice() {
        return String.format("%.2f", totalPrice);
    }
}

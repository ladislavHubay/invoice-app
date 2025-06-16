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
    private long id;

    @NotNull    // Tato hodnota nesmie byt Null.
    @NotBlank(message = "Item description is required")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String description;

    @NotNull(message = "Quantity is required")    // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    @DecimalMin(value = "0.01", inclusive = true, message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")    // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt Null + chybovu hlasku.
    @DecimalMin(value = "0.01", inclusive = true, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    @NotNull    // Tato hodnota nesmie byt Null.
    private BigDecimal totalPrice;

    @ManyToOne(optional = false)    // Entita InvoiceItem moze byt sucastou viacerych faktur (invoice) a podla toho sa ma vytvorit stlpec v databaze.
                                    // (optional = false) znamena ze entita InvoiceItem musi byt sucastou niektorej invoice.
    @JoinColumn(name = "invoice_id")    // Vytvori v tabulke InvoiceItem stlpec "invoice_id" (foreign key - cudzi kluc) v ktorej bude hodnota ID z Invoice.
    @JsonBackReference      // DOPLNIT ! ! !
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

    public String getFormattedUnitPrice() {
        return String.format("%.2f", unitPrice);
    }

    public String getFormattedTotalPrice() {
        return String.format("%.2f", totalPrice);
    }
}

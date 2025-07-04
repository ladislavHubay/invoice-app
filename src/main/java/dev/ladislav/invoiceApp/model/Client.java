package dev.ladislav.invoiceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity     // Spring boot tuto triedu oznaci ako entitu JPA - mapuje sa na tabulku v databaze.
@Data       // Spring boot pre tuto triedu vygeneruje potrebne gettre a settre...
public class Client {
    @Id     // Nastavi ako primarny kluc (s jedinecnou hodnotou) v databaze.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Zabezpeci automaticke generovanie jedinecnej hodnoty databazou.
    private Long id;

    @NotBlank(message = "Name cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String name;

    @NotBlank(message = "Street cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String street;

    @NotBlank(message = "House number cannot be empty")       // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String houseNumber;

    @NotBlank(message = "City cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String city;

    @NotBlank(message = "Postal code cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String postalCode;

    @NotBlank(message = "IČO cannot be empty")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\d{8}", message = "IČO must have 8 digits")     // Hodnota musi obsahovat 8 cislic.
    private String ico;

    @NotBlank(message = "DIČ cannot be empty")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\d{10}", message = "DIČ must have 10 digits")   // Hodnota musi obsahovat 10 cislic.
    private String dic;
}

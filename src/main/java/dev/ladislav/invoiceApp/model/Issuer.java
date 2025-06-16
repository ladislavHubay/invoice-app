package dev.ladislav.invoiceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity     // Spring boot tuto triedu oznaci ako entitu JPA - mapuje sa na tabulku v databaze.
@Data       // Spring boot pre tuto triedu vygeneruje potrebne gettre a settre
public class Issuer {
    @Id     // Nastavi ako primarny kluc (s jedinecnou hodnotou) v databaze.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Zabezpeci automaticke generovanie jedinecnej hodnoty.
    private Long id;

    @NotBlank(message = "Name cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String name;

    @NotBlank(message = "Street cannot be empty")       // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String street;

    @NotBlank(message = "Street cannot be empty")       // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String houseNumber;

    @NotBlank(message = "City cannot be empty")     // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String city;

    @NotBlank(message = "Postal code cannot be empty")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\d{3}\\s?\\d{2}", message = "Postal code must be in format 12345 or 123 45")    // Musia byt 3 cislice, medzera alebo bez medzery, 2 cislice (123 45 alebo 12345)
    private String postalCode;

    @NotBlank(message = "IČO cannot be empty")      // Pre validaciu nastavi podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\d{8}", message = "IČO must have 8 digits")     // Hodnota musi obsahovat 8 cislic.
    private String ico;

    @NotBlank(message = "DIČ cannot be empty")      // Pre validaciu nastavy podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\d{10}", message = "DIČ must have 10 digits")   // Hodnota musi obsahovat 10 cislic.
    private String dic;

    @NotBlank(message = "Phone cannot be empty")    // Pre validaciu nastavy podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Pattern(regexp = "\\+?\\d{9,15}", message = "Invalid phone number format")     // Hodnota moze ale nemusi obsahovat - iba jeden znak "+" (\\+?) a musi mat minimalne 9 a maximalne 15 cislic (\d{9,15}).
    private String phone;

    @NotBlank(message = "Email cannot be empty")    // Pre validaciu nastavy podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    @Email(message = "Invalid email format")    // Hodnota musi mat format email.
    private String email;

    @NotBlank(message = "Bank account cannot be empty")     // Pre validaciu nastavy podmienku ze tato hodnota nesmie byt biely znak (whitespace) + chybovu hlasku.
    private String bankAccount;

    /**
     * Metoda upravi IBAN na do tvaru s medzerami v akej sa IBAN ma zobrazovat (XXXX XXXX XXXX XXXX XXXX XXXX).
     * @return vrati naformatovaany IBAN
     */
    public String getFormattedIban() {
        if (bankAccount == null) {
            return "";
        }
        return bankAccount.replaceAll(".{4}(?!$)", "$0\u00A0");
    }
}

package dev.ladislav.invoiceApp;

import dev.ladislav.invoiceApp.model.Invoice;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component      // Oznacuje tuto triedu ako komponentu. Aby mohla byt automaticky injektovana pomocou @Autowired.
public class InvoiceValidator {
    /**
     * Metoda kontroluje a validuje spravnu postupnost datumov a nutnost mat minimalne jednu polozku pridanu na fakture.
     * @param invoice faktura.
     * @param bindingResult ulozene "errors" udaje. Uchovava vysledky validacie.
     */
    public void validateCustomBusinessRules(Invoice invoice, BindingResult bindingResult) {
        if(invoice.getDueDate() != null && invoice.getIssueDate() != null && invoice.getDeliveryDate() != null){
            if(invoice.getDueDate().isBefore(invoice.getIssueDate())){
                bindingResult.rejectValue("dueDate", "invalid.dueDate", "Due date must be after the issue date.");
            }
            if(invoice.getDueDate().isBefore(invoice.getDeliveryDate())){
                bindingResult.rejectValue("dueDate", "invalid.dueDate", "Due date must be after the Delivery date.");
            }
            if(invoice.getDeliveryDate().isBefore(invoice.getIssueDate())){
                bindingResult.rejectValue("deliveryDate", "invalid.deliveryDate", "delivery date must be after the issue date.");
            }
        }

        if(invoice.getItems().isEmpty()){
            bindingResult.rejectValue("items", "invalid.items", "Invoice must contain at least one item.");
        }
    }
}

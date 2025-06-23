package dev.ladislav.invoiceApp;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceValidatorTest {
    private InvoiceValidator validator;
    private Invoice invoice;
    private BindingResult bindingResult;

    @BeforeEach
    void setup() {
        validator = new InvoiceValidator();
        invoice = InvoiceTestDataFactory.createSampleInvoiceWithoutID();
        bindingResult = new BeanPropertyBindingResult(invoice, "invoice");
    }

    @Test
    void testValidInvoiceShouldHaveNoErrors() {
        validator.validateCustomBusinessRules(invoice, bindingResult);
        assertFalse(bindingResult.hasErrors(), "Expected no validation errors");
    }

    @Test
    void testDueDateBeforeIssueDateShouldHaveError() {
        invoice.setDueDate(invoice.getIssueDate().minusDays(1));
        validator.validateCustomBusinessRules(invoice, bindingResult);
        assertTrue(bindingResult.hasFieldErrors("dueDate"));
    }

    @Test
    void testDueDateBeforeDeliveryDateShouldHaveError() {
        invoice.setDueDate(invoice.getDeliveryDate().minusDays(1));
        validator.validateCustomBusinessRules(invoice, bindingResult);
        assertTrue(bindingResult.hasFieldErrors("dueDate"));
    }

    @Test
    void testDeliveryDateBeforeIssueDateShouldHaveError() {
        invoice.setDeliveryDate(invoice.getIssueDate().minusDays(1));
        validator.validateCustomBusinessRules(invoice, bindingResult);
        assertTrue(bindingResult.hasFieldErrors("deliveryDate"));
    }

    @Test
    void testInvoiceWithoutItemsShouldHaveError() {
        invoice.setItems(List.of());
        validator.validateCustomBusinessRules(invoice, bindingResult);
        assertTrue(bindingResult.hasFieldErrors("items"));
    }
}

package dev.ladislav.invoiceApp.controller;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.repository.InvoiceRepository;
import dev.ladislav.invoiceApp.service.InvoiceService;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvoiceControllerTest {
    /*
    private InvoiceService mockService;
    private InvoiceRepository mockRepository;
    private InvoiceController controller;

    @BeforeEach
    void setup() {
        mockService = mock(InvoiceService.class);
        mockRepository = mock(InvoiceRepository.class);
        controller = new InvoiceController(mockService, mockRepository);
    }

    @Test
    void testSubmitInvoiceValid_noErrors_shouldRedirect() {
        Invoice invoice = InvoiceTestDataFactory.createSampleInvoice();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = controller.submitInvoice(invoice, bindingResult);

        verify(mockService).createInvoice(invoice);
        assertEquals("redirect:/invoices", result);
    }

    @Test
    void testSubmitInvoice_withErrors_shouldReturnForm() {
        Invoice invoice = InvoiceTestDataFactory.createSampleInvoice();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = controller.submitInvoice(invoice, bindingResult);

        verify(mockService, never()).createInvoice(any());
        assertEquals("invoice_form", result);
    }

    @Test
    void testGetAllInvoices_shouldAddToModelAndReturnView(){
        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setName("Test 1");
        invoice1.setIssueDate(LocalDate.of(2025, 5, 13));
        invoice1.setDueDate(LocalDate.of(2025, 6, 13));
        invoice1.setAmount(new BigDecimal("1000"));
        invoice1.setStatus("nezaplatene");

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setName("Test 2");
        invoice2.setIssueDate(LocalDate.of(2025, 4, 13));
        invoice2.setDueDate(LocalDate.of(2025, 5, 13));
        invoice2.setAmount(new BigDecimal("1500"));
        invoice2.setStatus("zaplatene");

        List<Invoice> mockInvoices = List.of(invoice1, invoice2);

        when(mockService.getAllInvoices()).thenReturn(mockInvoices);
        Model model = mock(Model.class);

        String viewName = controller.listInvoices(model);

        verify(mockService).getAllInvoices();
        verify(model).addAttribute("invoices", mockInvoices);
        assertEquals("invoice_list", viewName);
    }

    @Test
    void testDeleteInvoice_shouldDeleteAndRedirect(){
        long idToDelete = 1L;

        String result = controller.deleteInvoice(idToDelete);

        verify(mockRepository).deleteById(idToDelete);
        assertEquals("redirect:/invoices", result);
    }

    @Test
    void testShowInvoiceForm_addsEmptyInvoiceAndReturnsForm(){
        Model model = mock(Model.class);

        String viewName = controller.showCreateForm(model);

        verify(model).addAttribute(eq("invoice"), any(Invoice.class));
        assertEquals("invoice_form", viewName);
    }
     */
}

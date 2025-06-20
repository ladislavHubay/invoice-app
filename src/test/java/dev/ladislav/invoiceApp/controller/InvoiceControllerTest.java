package dev.ladislav.invoiceApp.controller;

import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.repository.InvoiceRepository;
import dev.ladislav.invoiceApp.service.InvoiceService;
import dev.ladislav.invoiceApp.service.PdfExportService;
import dev.ladislav.invoiceApp.util.InvoiceTestDataFactory;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvoiceControllerTest {
    private InvoiceService mockService;
    private InvoiceRepository mockRepository;
    private InvoiceController controller;
    private PdfExportService mockPdfExportService;

    @BeforeEach
    void setup() {
        mockService = mock(InvoiceService.class);
        mockRepository = mock(InvoiceRepository.class);
        mockPdfExportService = mock(PdfExportService.class);
        controller = new InvoiceController(mockService, mockRepository, mockPdfExportService);
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
        Invoice invoice = InvoiceTestDataFactory.createSampleInvoice();

        List<Invoice> mockInvoices = List.of(invoice);

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

    @Test
    void testExportInvoice_shouldCallExportService() {
        long invoiceId = 1L;
        HttpServletResponse response = mock(HttpServletResponse.class);

        controller.exportInvoice(invoiceId, response);

        verify(mockPdfExportService).exportInvoiceToPdf(invoiceId, response);
    }
}

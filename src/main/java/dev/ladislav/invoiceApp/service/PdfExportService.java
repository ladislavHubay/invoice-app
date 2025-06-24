package dev.ladislav.invoiceApp.service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import dev.ladislav.invoiceApp.exception.ResourceNotFoundException;
import dev.ladislav.invoiceApp.model.Invoice;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PdfExportService {
    private final SpringTemplateEngine templateEngine;
    private final InvoiceService invoiceService;

    public PdfExportService(SpringTemplateEngine templateEngine, InvoiceService invoiceService) {
        this.templateEngine = templateEngine;
        this.invoiceService = invoiceService;
    }

    /**
     * Metoda vygeneruje pdf subor na zaklade ID.
     * @param id jedinecne, generovane ID pre kazdu fakturu pri vytvarani faktury. Pre tuto metodu sa ziskava z url.
     * @param response objekt triedy HttpServletResponse, do ktoreho sa zapise vygenerovany pdf subor.
     */
    public void exportInvoiceToPdf(long id, HttpServletResponse response) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        if (invoice == null) {
            throw new ResourceNotFoundException("Faktura s ID " + id + " neexistuje.");
        }

        String styles;
        try (InputStream inputStream = new ClassPathResource("/static/css/pdf_style.css").getInputStream()) {
            styles = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Nepodarilo sa nacitat CSS styly: " + e.getMessage());
        }

        Context context = new Context();
        context.setVariable("invoice", invoice);
        context.setVariable("styles", styles);

        String htmlContent = templateEngine.process("invoice_template", context);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + invoice.getInvoiceNumber() + ".pdf");

        try (OutputStream os = response.getOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFont(() -> {
                try {
                    return new ClassPathResource("fonts/DejaVuSans.ttf").getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, "DejaVu Sans", 400, BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovani PDF: " + e.getMessage());
        }
    }
}

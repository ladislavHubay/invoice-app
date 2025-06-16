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
     * Metoda sa spusti ak MVC zachyti poziadavku (uzivatel klikne na button "Export to PDF") s url "/export/{id}".
     * Metoda pomocou @PathVariable ziska ID s url faktury a data z faktury s tymto id zapise do pdf.
     * @param id jedinecne, generovane ID pre kazdu fakturu pri vytvarani faktury. Pre tuto metodu sa ziskava z url.
     * @param response objekt triedy HttpServletResponse, ktory umoznuje zapis dat, v tomto pripade do pdf.
     */
    public void exportInvoiceToPdf(long id, HttpServletResponse response) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        if (invoice == null) {
            throw new ResourceNotFoundException("Faktura s ID " + id + " neexistuje.");
        }

        // 1. Nacitaj obsah CSS suboru ako String
        String styles;
        try (InputStream inputStream = new ClassPathResource("/static/css/style.css").getInputStream()) {
            styles = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Nepodarilo sa nacitat CSS styly: " + e.getMessage());
        }

        // 2. Nastav premenne do Thymeleaf kontextu
        Context context = new Context();
        context.setVariable("invoice", invoice);
        context.setVariable("styles", styles);

        // 3. Vygeneruj HTML s vlozenymi stylmi
        String htmlContent = templateEngine.process("invoice_template", context);

        // 4. Exportuj do PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");

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

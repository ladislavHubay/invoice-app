package dev.ladislav.invoiceApp.controller;

import dev.ladislav.invoiceApp.InvoiceValidator;
import dev.ladislav.invoiceApp.model.Client;
import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.model.InvoiceItem;
import dev.ladislav.invoiceApp.model.Issuer;
import dev.ladislav.invoiceApp.repository.InvoiceRepository;
import dev.ladislav.invoiceApp.service.InvoiceService;
import dev.ladislav.invoiceApp.service.PdfExportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller     // oznacuje triedu ako webovy kontroler - Spring boot triedu zaregistruje ako súčasť MVC.
@RequestMapping("/invoices")    // nastavuje zakladnu URL adresu pre vsetky metody v tejto triede
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final PdfExportService pdfExportService;

    private final InvoiceValidator invoiceValidator;

    @Autowired      // vytvara objekt (netreba manualne cez "new" vytvarat objekty).
    public InvoiceController(InvoiceService invoiceService, InvoiceRepository invoiceRepository, PdfExportService pdfExportService, InvoiceValidator invoiceValidator) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.pdfExportService = pdfExportService;
        this.invoiceValidator = invoiceValidator;
    }

    /**
     * Metoda sa spusti pri kliknuti na new invoice (url: /invoices/new). Vytvory prazdny formular (invoice) a
     * vlozi do objektu model a prevedie do HTML. Nasledne zobrazi formular (invoice) podla HTML sablony "invoice_form"
     * @param model Spring objekt, ktory formular (invoice) prevedie do HTML sablony.
     * @return vrati retazec (HTML sablonu "invoice_form"), ktora sa ma zobrazit.
     */
    @GetMapping("/new")     // Pri kliknuti na "new invoice" (url: /invoices/new) Spring boot poziadavku zachyti a spusti tuto metodu.
    public String showCreateForm(Model model) {
        Invoice invoice = new Invoice();
        invoice.setIssueDate(LocalDate.now());

        String generatedNumber = invoiceService.generateInvoiceNumber(invoice.getIssueDate());
        invoice.setInvoiceNumber(generatedNumber);
        invoice.setVariableSymbol(generatedNumber);

        List<InvoiceItem> items = new ArrayList<>();

        items.add(new InvoiceItem());
        invoice.setItems(items);

        invoice.setIssuer(new Issuer());
        invoice.setClient(new Client());

        model.addAttribute("invoice", invoice);

        return "invoice_form";
    }

    /**
     * Metoda sa zavola ak pouzivatel odosle formular (klikne na submit) pomocou
     * HTTP POST poziadavky. - v HTML je definovane: <form method="post" action="/invoices/new">.
     * Metoda prijme udaje z formulara ako objekt typu Invoice a vykona validaciu. Ak validacia zlyha,
     * vrati spat stranku s formularom ("invoice_form") a zobrazi chyby. Ak validacia prejde ulozi fakturu
     * a presmeruje na stranku "/invoices" so zoznamom faktur.
     * @param invoice objekt triedy Invoice naplneny udajmi z formulara.
     * @param bindingResult objekt obsahuje vysledky validacie.
     * @return vrati bud formular s chybami alebo ulozi formular a presmeruje na stranku "/invoices" so zoznamom faktur,
     * podla vysledku validacie.
     */
    @PostMapping("/new")     // Pri odoslani dat (kliknuti na submit) Spring boot toto zachyti a spusti tuto metodu.
    public String submitInvoice(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult) {
                                    // @Valid - vykona validacie
                                    // @ModelAttribute - vytvory novy invoice a naplni ho datami z formulara.

        invoiceValidator.validateCustomBusinessRules(invoice, bindingResult);

        if(bindingResult.hasErrors()) {
            return "invoice_form";
        }
        invoiceService.createInvoice(invoice);
        return "redirect:/invoices";
    }

    /**
     * Metoda sa zavola ak uzivatel navstivy "/invoices". URL je zabezpecena v anotacii tridy (@RequestMapping("/invoices")).
     * Metoda nacita zoznam vsetkych faktur a nasledne prida do 'model', ktory prevedie data do HTML sablony "invoice_list" a
     * zobrazi tento zoznam faktur.
     * @param model Spring objekt, ktory data z formularu prevedie do HTML sablony.
     * @return vrati nazov HTML sablony ktora sa ma zobrazit.
     */
    @GetMapping     // Pri spusteni URL "/invoices" (dane v anotacii triedy @RequestMapping("/invoices")) Spring boot spusti tuto metodu.
    public String listInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoice_list";
    }

    /**
     * Metoda sa zavola ak uzivatel odosle poziadavku (klikne na delete) url "/delete/{id}" (id sa vlozi do
     * premennej automacitcky podla id faktury). Metoda vymaze fakturu s ID zo zoznamu (databazy) a nasledne
     * presmeruje (znovu sa nacita) url "redirect:/invoices".
     * @param id je jedinecne ID generovane pre kazdu fakturu pri vytvoreni faktury. Pri vymazani pomocou @PathVariable
     *           sa ziskava priamo z url linku.
     * @return vrati url link ktory sa ma nacitat.
     */
    @PostMapping("/delete/{id}")    // Pri POST poziadavke (kliknuti na button delete - url "/delete/{id}") Spring boot spusti tuto metodu.
    public String deleteInvoice(@PathVariable long id) {    // @PathVariable z url zisti ID a vlozi do premennej id
        invoiceRepository.deleteById(id);
        return "redirect:/invoices";
    }

    @GetMapping("/export/{id}")
    public void exportInvoice(@PathVariable long id, HttpServletResponse response) {
        pdfExportService.exportInvoiceToPdf(id, response);
    }
}

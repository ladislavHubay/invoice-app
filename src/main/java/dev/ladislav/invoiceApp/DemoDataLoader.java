package dev.ladislav.invoiceApp;

import dev.ladislav.invoiceApp.model.Client;
import dev.ladislav.invoiceApp.model.Invoice;
import dev.ladislav.invoiceApp.model.InvoiceItem;
import dev.ladislav.invoiceApp.model.Issuer;
import dev.ladislav.invoiceApp.repository.InvoiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Component
public class DemoDataLoader implements CommandLineRunner {
    private final InvoiceRepository invoiceRepository;

    public DemoDataLoader(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (invoiceRepository.count() == 0) {
            Random random = new Random();

            for (int i = 1; i <= 3; i++) {
                Invoice invoice = new Invoice();
                Issuer issuer = new Issuer();
                Client client = new Client();
                InvoiceItem invoiceItem = new InvoiceItem();

                issuer.setName("Issuer " + i);
                issuer.setCity("City issuer " + i);
                issuer.setStreet("Street issuer " + i);
                issuer.setHouseNumber(String.valueOf(random.nextInt(900) + 100));
                issuer.setPostalCode(String.valueOf(random.nextInt(90000) + 10000));
                issuer.setEmail("Issuer" + i + "@mail.com");
                issuer.setPhone("0905123456");
                issuer.setBankAccount("ABCD12345678901234567890");
                issuer.setIco("12345678");
                issuer.setDic("9876543210");

                client.setName("Client " + i);
                client.setCity("City client " + i);
                client.setStreet("Street client " + i);
                client.setHouseNumber(String.valueOf(random.nextInt(900) + 100));
                client.setPostalCode(String.valueOf(random.nextInt(90000) + 10000));
                client.setIco("87654321");
                client.setDic("1234567890");

                invoiceItem.setDescription("description item " + i);
                invoiceItem.setQuantity(new BigDecimal(2 * i));
                invoiceItem.setUnitPrice(new BigDecimal(10 * i));

                invoice.setIssuer(issuer);
                invoice.setClient(client);
                invoice.addItem(invoiceItem);
                invoice.setInvoiceNumber("200620250" + i);
                invoice.setVariableSymbol("200620250" + i);
                invoice.setIssueDate(LocalDate.of(2025, 5, random.nextInt(28) + 1));
                invoice.setDeliveryDate(LocalDate.of(2025, 6, random.nextInt(28) + 1));
                invoice.setDueDate(LocalDate.of(2025, 7, random.nextInt(28) + 1));

                invoiceRepository.save(invoice);
            }
        }
    }
}

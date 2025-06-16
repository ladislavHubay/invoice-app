package dev.ladislav.invoiceApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication		// Zapne vsetko co triedy aplikacie potrebuju.
public class InvoiceAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvoiceAppApplication.class, args);
	}
}

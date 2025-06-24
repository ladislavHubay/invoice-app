# Fakturačná aplikácia v Spring Boot

Jednoduchá webová aplikácia na správu faktúr, vytvorená v prostredí Spring Boot. Umožňuje vytvárať, validovať, zobrazovať a exportovať faktúry do PDF.

## 🎯 Funkcionalita

- Vytváranie novej faktúry s údajmi o vystaviteľovi, klientovi a položkách
- Validácia údajov na strane servera (anotácie aj vlastný validátor)
- Zobrazenie zoznamu faktúr
- Vymazanie faktúry
- Export faktúry do PDF pomocou šablóny
- Automatické generovanie čísla faktúry

## 🛠 Použité technológie

- **Java 17**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
    - Spring Thymeleaf
- **Hibernate (JPA)**
- **SQLite** (ako databáza)
- **Thymeleaf** (HTML šablóny)
- **OpenHTMLtoPDF** (generovanie PDF z HTML + CSS)
- **Lombok** (anotácie `@Data`, `@Getter`, `@Setter` atď.)
- **Maven** (buildovanie projektu)

## 🔧 Stiahnutie aplikácie

1. Nainštaluj JDK 17 a Maven.
2. Klonuj repozitár:
   ```bash
   git clone https://github.com/ladislavHubay/invoice-app.git
   cd invoice-app

## ▶️ Spustenie

1. Spusti aplikáciu:

   ```bash
   mvn spring-boot:run
   
2. Otvor link:

   ```bash
   http://localhost:8080/invoices
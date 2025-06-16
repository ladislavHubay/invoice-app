package dev.ladislav.invoiceApp.exception;

/**
 * Vlastna vynimka ktora dedi z RuntimeException, ktorej odovzda spravu (message).
 * Pouziva sa napr. ak sa vyzaduje faktura s nexistujucou ID.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

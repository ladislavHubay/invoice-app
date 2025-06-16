package dev.ladislav.invoiceApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

@ControllerAdvice       // Tato trieda je oznacena ako trieda na riesenie vynimiek. Spring Boot spusti ked nastane nejaka vynimka.
public class GlobalExceptionHandler {

    /**
     * Metoda spracovava vynimky ak chyba zdroj (napr. ked faktura neexistuje).
     * @param ex povodna konkretna vyhodena vynimka.
     * @return vrati objekt ResponseEntity.
     */
    @ExceptionHandler(ResourceNotFoundException.class)      // Spring boot spusti tuto metodu pri vyhodeni vynimky.
                                                            // Parameter (ResourceNotFoundException.class) urcuje pri akom type
                                                            // vynimky sa ma metoda spustit.
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Metoda spracovava vynimky pri neuspesnej validacie.
     * @param ex povodna konkretna vyhodena vynimka.
     * @return vrati objekt ResponseEntity.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)    // Spring boot spusti tuto metodu pri vyhodeni vynimky.
                                                                // Parameter (MethodArgumentNotValidException.class) urcuje
                                                                // pri akom type vynimky sa ma metoda spustit.
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse("VALIDATION_FAILED", errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

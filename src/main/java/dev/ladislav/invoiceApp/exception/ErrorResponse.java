package dev.ladislav.invoiceApp.exception;

public class ErrorResponse {
    private String message;
    private String error;

    public ErrorResponse(String error, String message) {
        this.message = message;
        this.error = error;
    }

    /**
     * Metoda umoznuje pristup k doplnujucej sprave k chybe.
     * @return vracia spravu o chybe.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Metoda nastavy doplnujucu spravu k chybe.
     * @param message text spravy.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Metoda ziskava povodny / hlavny popis chyby.
     * @return text chyby.
     */
    public String getError() {
        return error;
    }

    /**
     * Metoda nastavy povodny / hlavny popis chyby.
     * @param error text chyby.
     */
    public void setError(String error) {
        this.error = error;
    }
}

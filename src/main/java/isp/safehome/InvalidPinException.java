package isp.safehome;

public class InvalidPinException extends Exception {
    private String message;
    public InvalidPinException(String message) {
        super(message);

    }

}

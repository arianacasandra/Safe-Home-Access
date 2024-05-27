package isp.safehome;

public class TooManyAttemptsException extends Exception{
    private String message;
    public TooManyAttemptsException(String message) {
        super(message);

    }
}

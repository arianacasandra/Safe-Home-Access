package isp.safehome;

public class TenantAlreadyExistsException extends Exception {
    String message;
    TenantAlreadyExistsException(String message)
    {
        super(message);
    }
}

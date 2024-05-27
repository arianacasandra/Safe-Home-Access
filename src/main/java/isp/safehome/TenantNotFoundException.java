package isp.safehome;
public class TenantNotFoundException extends Exception{
    private String message;
    public TenantNotFoundException(String message)
    {
        super(message);
    }
}

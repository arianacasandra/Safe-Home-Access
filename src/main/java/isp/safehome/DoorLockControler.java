package isp.safehome;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DoorLockControler implements ControllerInterface{
    Door door= new Door();
    private ArrayList<AccessLog> accessLogs = new ArrayList<>();
    private Map<Tenant, AccessKey> validAccess = new HashMap<>();
    private int pinEnteredCount = 0;

    public int getPinEnteredCount() {
        return pinEnteredCount;
    }

    public void setPinEnteredCount(int pinEnteredCount) {
        this.pinEnteredCount = pinEnteredCount;
    }

    public ArrayList<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(ArrayList<AccessLog> accessLogs) {
        this.accessLogs = accessLogs;
    }

    @Override
    public DoorStatus enterPin(String pin) throws TooManyAttemptsException, InvalidPinException {
        boolean matched = false;

        setPinEnteredCount(getPinEnteredCount() + 1);
        AccessLog accessLog = new AccessLog();
        accessLog.setDateTime(LocalDateTime.now());
        accessLog.setTenantName("-");
        accessLog.setOperation("enterPin");
        accessLog.setErrorMessage("-");
        accessLog.setDoorStatus(door.getStatus());

        if (pin.equals(MASTER_KEY)) {
            door.unlockDoor();
            setPinEnteredCount(0);
            accessLog.setTenantName("Master");
            accessLog.setErrorMessage("Access granted");
            accessLogs.add(accessLog);
            return door.getStatus();
        }

        if (getPinEnteredCount() == 3) {
            door.lockDoor();
            accessLog.setErrorMessage("TooManyAttemptsException");
            accessLogs.add(accessLog);
            throw new TooManyAttemptsException("TooManyAttemptsException");
        }

        for (Map.Entry<Tenant, AccessKey> entry : validAccess.entrySet()) {
            if (entry.getValue().getPin().equals(pin)) {
                if (door.getStatus() == DoorStatus.OPEN) {
                    door.lockDoor();
                } else {
                    door.unlockDoor();
                }
                matched = true;
                setPinEnteredCount(0);
                accessLog.setTenantName(entry.getKey().getName());
                accessLog.setErrorMessage("Access granted");
                accessLogs.add(accessLog);
                break;
            }
        }

        if (!matched && getPinEnteredCount()!=3) {
            door.lockDoor();
            accessLog.setErrorMessage("InvalidPinException");
            accessLogs.add(accessLog);
            throw new InvalidPinException("InvalidPinException");
        }

        return door.getStatus();
    }




    @Override
    public void addTenant(String pin, String name) throws TenantAlreadyExistsException {
        Tenant tenant = new Tenant(name);
        AccessKey accessKey = new AccessKey(pin);
        AccessLog accessLog = new AccessLog();
        accessLog.setDateTime(LocalDateTime.now());
        accessLog.setErrorMessage("-");
        accessLog.setTenantName(name);
        accessLog.setOperation("-");
        accessLog.setDoorStatus(door.getStatus());


        for (Map.Entry<Tenant, AccessKey> entry : validAccess.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                accessLog.setErrorMessage("TenantAlreadyExistsException");
                throw new TenantAlreadyExistsException("TenantAlreadyExistsException");
            }
        }

        accessLog.setErrorMessage("added");
        accessLogs.add(accessLog);
        validAccess.put(tenant, accessKey);
    }
    @Override
    public void removeTenant(String name) throws TenantNotFoundException {
    boolean delete = false;



        Iterator<Map.Entry<Tenant, AccessKey>> iterator = validAccess.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Tenant, AccessKey> entry = iterator.next();
            if (entry.getKey().getName().equals(name)) {
                iterator.remove();

                delete = true;
                break;
            }
        }
        if(delete == false) {
            throw new TenantNotFoundException("TenantNotFoundException");

        }
        AccessLog accessLog = new AccessLog();
        accessLog.setTenantName(name);
        accessLog.setDateTime(LocalDateTime.now());
        accessLog.setDoorStatus(door.getStatus());
        accessLog.setOperation("removed");
        accessLog.setErrorMessage("-");
        accessLogs.add(accessLog);
    }

    public void Display()
    {
        Iterator<Map.Entry<Tenant, AccessKey>> it =validAccess.entrySet().iterator();
        while (it.hasNext()) {
           Map.Entry<Tenant, AccessKey> entry = it.next();
            Tenant key = entry.getKey();
            AccessKey value = entry.getValue();
            System.out.println("Key: " + key.getName() + ", Value: " + value.getPin());
        }
    }


}

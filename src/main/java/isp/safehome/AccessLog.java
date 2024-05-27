package isp.safehome;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccessLog {
    private String tenantName;
    private LocalDateTime dateTime;
    private String operation;
    private DoorStatus doorStatus;
    private String errorMessage;

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getOperation() {
        return operation;
    }
}

package isp.safehome;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SafeHome {
    private static final int USER_TYPE_ADMIN = 1;
    private static final int USER_TYPE_TENANT = 2;

    private static DoorLockControler controller = new DoorLockControler();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to SafeHome!");

        while (true) {
            System.out.println("Please select user type:");
            System.out.println("1. Admin");
            System.out.println("2. Tenant");
            System.out.print("> ");

            int userType = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (userType) {
                case USER_TYPE_ADMIN:
                    handleAdminUser(scanner);
                    break;

                case USER_TYPE_TENANT:
                    handleTenantUser(scanner);
                    break;

                default:
                    System.out.println("Invalid user type.");
                    break;
            }
        }
    }

    private static void handleAdminUser(Scanner scanner) {
        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Add tenant");
            System.out.println("2. Remove tenant");
            System.out.println("3. View access logs");
            System.out.println("4. Go back");
            System.out.print("> ");

            int action = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (action) {
                case 1:
                    handleAddTenant(scanner);
                    break;

                case 2:
                    handleRemoveTenant(scanner);
                    break;

                case 3:
                    handleViewAccessLogs();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid action.");
                    break;
            }
        }
    }

    private static void handleAddTenant(Scanner scanner) {
        System.out.print("Enter tenant name: ");
        String name = scanner.nextLine();

        System.out.print("Enter tenant PIN: ");
        String pin = scanner.nextLine();

        try {
            controller.addTenant(pin, name);
            System.out.println("Tenant added successfully.");
        } catch (TenantAlreadyExistsException e) {
            System.out.println("Error: Tenant already exists.");
        }
    }

    private static void handleRemoveTenant(Scanner scanner) {
        System.out.print("Enter tenant name: ");
        String name = scanner.nextLine();

        try {
            controller.removeTenant(name);
            System.out.println("Tenant removed successfully.");
        } catch (TenantNotFoundException e) {
            System.out.println("Error: Tenant not found.");
        }
    }

    private static void handleViewAccessLogs() {
        for (AccessLog accessLog : controller.getAccessLogs()) {
            System.out.println(accessLog.getTenantName()+" "+accessLog.getDateTime()+" "+accessLog.getOperation()+" "+accessLog.getDoorStatus()+" "+accessLog.getErrorMessage());
        }
    }

    private static void handleTenantUser(Scanner scanner) {
        while (true) {
            System.out.println("Please enter PIN:");
            System.out.print("> ");

            String pin = scanner.nextLine();

            try {
                DoorStatus status = controller.enterPin(pin);
                System.out.println("Door status: " + status);
                return;
            } catch (TooManyAttemptsException e) {
                System.out.println("Error: Too many attempts. Please contact admin.");
                return;
            } catch (InvalidPinException e) {
                System.out.println("Error: Invalid PIN.");
            }
        }
    }
}











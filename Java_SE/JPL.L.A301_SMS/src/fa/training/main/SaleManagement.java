package fa.training.main;

import fa.training.dao.*;
import fa.training.entities.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SaleManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        LineItemDAO lineItemDAO = new LineItemDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();

        boolean running = true;
        while (running) {
            System.out.println("\n========= SALE MANAGEMENT SYSTEM =========");
            System.out.println("1. List all customers");
            System.out.println("2. List orders by customer ID");
            System.out.println("3. List line items by order ID");
            System.out.println("4. Compute order total (via UDF)");
            System.out.println("5. Add new customer");
            System.out.println("6. Update customer name");
            System.out.println("7. Delete customer (Cascade)");
            System.out.println("8. List all products");
            System.out.println("0. Exit");
            System.out.print("Please choose an option: ");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    separator("All Customers");
                    customerDAO.getAllCustomer().forEach(item -> System.out.println(item.toString()));
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    int custId = Integer.parseInt(scanner.nextLine());
                    separator("Orders for Customer ID: " + custId);
                    orderDAO.getAllOrdersByCustomerId(custId).forEach(item -> System.out.println(item.toString()));
                    break;
                case 3:
                    System.out.print("Enter Order ID: ");
                    int orderId = Integer.parseInt(scanner.nextLine());
                    separator("Line Items for Order ID: " + orderId);
                    lineItemDAO.getAllItemsByOrderId(orderId).forEach(item -> System.out.println(item.toString()));
                    break;
                case 4:
                    System.out.print("Enter Order ID to compute total: ");
                    int oId = Integer.parseInt(scanner.nextLine());
                    Double total = lineItemDAO.computeOrderTotal(oId);
                    System.out.println("Computed Total Price: " + total);
                    break;
                case 5:
                    System.out.print("Enter New Customer Name: ");
                    String name = scanner.nextLine();
                    boolean added = customerDAO.addCustomer(new Customer(0, name));
                    System.out.println("Add Result: " + (added ? "Success" : "Failed"));
                    break;
                case 6:
                    System.out.print("Enter Customer ID to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    boolean updated = customerDAO.updateCustomer(new Customer(updateId, newName));
                    System.out.println("Update Result: " + (updated ? "Success" : "Failed"));
                    break;
                case 7:
                    System.out.print("Enter Customer ID to delete: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    boolean deleted = customerDAO.deleteCustomer(delId);
                    System.out.println("Delete Result: " + (deleted ? "Success" : "Failed"));
                    break;
                case 8:
                    separator("All Products");
                    productDAO.getAllProduct().forEach(item -> System.out.println(item.toString()));
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting system");
                    break;
                default:
                    System.out.println("Option not found. Try again.");
            }
        }
        scanner.close();
    }

    private static void separator(String title) {
        System.out.println("\n----------------------------------------");
        System.out.println("  " + title);
        System.out.println("----------------------------------------");
    }
}
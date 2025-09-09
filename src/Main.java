import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.SuppliersDAO;

import model.Customers;
import model.Products;
import model.Supplier;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private int customer_id = 0;   // keeps track of logged in user
    private boolean login = false; // login flag
    private Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().startApp(); // ✅ start app using object (no static mess)
    }

    // ================= MENU LOOP =================
    private void startApp() {
        try {
            System.out.println("Ecommerce store !!!");
            char c = 'y';
            while (c == 'y') {
                System.out.println("------MENU------");
                System.out.println(" 1.Add Product\n 2.View product\n 3.Suppliers\n 4.Purchase \n 5.Customer Login\n 6.Exit");
                System.out.print("Choose option : ");

                int key;
                try {
                    key = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Enter a number...");
                    scan.nextLine();
                    continue;
                }

                switch (key) {
                    case 1 -> productEntry();
                    case 2 -> new ProductDAO().viewProducts();
                    case 3 -> new SuppliersDAO().viewSupplier();
                    case 4 -> purchaseProduct();
                    case 5 -> customerLogin();
                    case 6 -> {
                        c = 'n';
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again...");
                }
            }
        } catch (Exception e) {
            System.out.println("Bruh error !..." + e);
        } finally {
            scan.close();
        }
    }

    // ================= CUSTOMER LOGIN =================
    private void customerLogin() {
        while (true) {
            try {
                System.out.println(" 1.Sign up\n 2.Login");
                int choice = scan.nextInt();
                scan.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.println("Signup : ");
                        System.out.print("Enter username : ");
                        String name = scan.nextLine();
                        System.out.print("Enter password : ");
                        String pass = scan.nextLine();

                        CustomerDAO customerDAO = new CustomerDAO();
                        if (!customerDAO.checkCustomer(name)) {
                            Customers customer = new Customers(name, pass);
                            customerDAO.signUp(customer);
                        } else {
                            System.out.println("Bruh you already exist...I mean the customer " + name);
                        }
                    }
                    case 2 -> {
                        if (this.login) {
                            System.out.println("You already logged in !!!!");
                            return;
                        }
                        CustomerDAO customerDAO = new CustomerDAO();
                        while (true) {
                            System.out.println("Login : ");
                            System.out.print("Enter username : ");
                            String name = scan.nextLine();
                            System.out.print("Enter password : ");
                            String pass = scan.nextLine();
                            Customers customers = new Customers(name, pass);
                            this.customer_id = customerDAO.login(customers);
                            if (this.customer_id == 0) {
                                System.out.println("Customer name or password incorrect !!!");
                                continue;
                            }
                            this.login = true;
                            System.out.println("Login Successful ✅");
                            break;
                        }
                    }
                    default -> System.out.println("Invalid choice! Try again...");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!! ,Enter correct input to continue....");
                scan.nextLine();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // ================= PURCHASE PRODUCT =================
    private void purchaseProduct() {
        try {
            if (!this.login) {
                System.out.println("⚠️ You need to login before purchasing!");
                return;
            }
            new ProductDAO().searchProduct(this.customer_id);
        } catch (SQLException e) {
            System.out.println("Error while purchasing : " + e);
        }
    }

    // ================= ADD PRODUCT =================
    private void productEntry() {
        try {
            Products Products = new Products();

            System.out.println("Enter product details :");
            scan.nextLine(); // flush

            System.out.print("Enter product name : ");
            Products.setName(scan.nextLine());

            System.out.print("Enter product price : ");
            Products.setPrice(scan.nextInt());

            System.out.print("Enter quantity for stocking : ");
            Products.setStock(scan.nextInt());
            scan.nextLine();

            Supplier supplier = new Supplier();
            System.out.print("Enter supplier name : ");
            supplier.setName(scan.nextLine());
            System.out.print("Enter supplier phone number : ");
            supplier.setPhone(scan.nextLine());

            new ProductDAO(Products, supplier);
        } catch (Exception e) {
            System.out.println("Error in adding products : " + e);
        }
    }
}

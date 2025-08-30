import DAO.ProductDAO;
import DAO.SuppliersDAO;
import Database.Db;
import model.Products;
import model.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try{
            System.out.println("Ecommerce store !!!");
            char c = 'y';
            int key ;
            while (c == 'y'){
                System.out.println("------MENU------");
                System.out.println(" 1.Add Product\n 2.View product\n 3.Suppliers\n 4.Purchase \n 5.Exit");
                System.out.print("Choose option : ");
                key =scan.nextInt();
                switch(key){
                    case 1:
                        productEntry();
                        break;
                    case 2:
                        new ProductDAO().viewProducts();
                        break;
                    case 3:
                        new SuppliersDAO().viewSupplier();
                        break;
                    case 4:
                        purchaseProduct();
                    case 5:
                        c ='n';
                        return;
                }

            }


        }catch(Exception e){
            System.out.println("Bruh error !..."+ e);
        }
        finally {
            scan.close();
        }
    }

    private static void purchaseProduct() throws SQLException {


        new ProductDAO().searchProduct();

    }

    private static void productEntry() {
        Scanner scan = new Scanner(System.in);
        try {


            Products Products = new Products();


            System.out.println("Enter product details :");

            System.out.print("Enter product name :");
            Products.setName(scan.nextLine());
            System.out.print("Enter product price : ");
            Products.setPrice(scan.nextInt());
            scan.nextLine();
            System.out.print("Enter quantity for stocking : ");
            Products.setStock(scan.nextInt());
            scan.nextLine();

            Supplier supplier = new Supplier();

            System.out.print("Enter supplier name : ");
            supplier.setName(scan.nextLine());
            System.out.print("Enter supplier phone number : ");
            supplier.setPhone(scan.nextLine());

            ProductDAO productDAO = new ProductDAO(Products, supplier);
        }catch (Exception e){
            System.out.println("Error in displaying products : "+ e);
        }

    }
}

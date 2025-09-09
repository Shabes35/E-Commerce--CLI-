package DAO;

import Database.Db;
import model.Customers;
import model.Orders;
import model.Products;
import model.Supplier;

import java.sql.*;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class ProductDAO{

    public  ProductDAO (){

    }
    public ProductDAO(Products product,Supplier supplier) {
        Db db = new Db();
        try {
            Connection con = db.getConnection();
            SuppliersDAO supcheck = new SuppliersDAO();
            if(!supcheck.supplierExist(supplier.getName(), supplier.getPhone())){
                SuppliersDAO suppliersDAO = new SuppliersDAO(supplier);
            }
            String sql ="Insert into products (product_name,selling_price,stock,supplier_id) " +
                    "values(?,?,?,(select supplier_id from suppliers where supplier_name = ? and phone = ?))";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,product.getName());
            stmt.setInt(2,product.getPrice());
            stmt.setInt(3,product.getStock());
            stmt.setString(4,supplier.getName());
            stmt.setString(5,supplier.getPhone());
            stmt.executeUpdate();
            stmt.close();
            con.close();



        }catch (SQLException e){
            System.out.println(" bruh... error while adding products  !! : "+ e);
        }
    }



    private boolean checkSupplier(Connection con ,int supplierId) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from suppliers where supplier_id = "+supplierId);
        return rs.next();
    }

    public void viewProducts() throws SQLException {
        System.out.println("Product List :");
        Db db= new Db();
        Connection con = db.getConnection();


        PreparedStatement stmt = con.prepareStatement(
                "SELECT p.product_id, p.product_name, p.selling_price, p.stock, s.supplier_name " +
                        "FROM products AS p " +
                        "JOIN suppliers AS s ON s.supplier_id = p.supplier_id"
        );
        ResultSet rs = stmt.executeQuery();

        System.out.printf("%-5s %-20s %-10s %-10s %-15s%n",
                "ID", "Product Name", "Price", "Stock", "Supplier Name");
        System.out.println("------------------------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d %-20s %-10d %-10d %-15s%n",
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("selling_price"),
                    rs.getInt("stock"),
                    rs.getString("supplier_name"));
        }

        System.out.println();
        rs.close();
        stmt.close();
        con.close();

    }

    public void searchProduct(int cusId) throws SQLException {
        Db db = new Db();
        Connection con =db.getConnection();
        Scanner scan = new Scanner(System.in);
        while(true) {

            System.out.println("Enter product name : ");
            String s = scan.nextLine();
            String query = "SELECT p.product_id, p.product_name, p.selling_price, p.stock, s.supplier_name FROM products AS p JOIN suppliers AS s ON s.supplier_id = p.supplier_id where p.product_name like ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + s + "%");
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("Product not found !!!");
                continue;
            }


            System.out.println("Matching products with name " + s);

            System.out.printf("%-5s %-20s %-10s %-10s %-15s%n",
                    "ID", "Product Name", "Price", "Stock", "Supplier Name");
            System.out.println("------------------------------------------------------------------");
            Set<Integer> ids = new HashSet<>();
            while (rs.next()) {
                ids.add(rs.getInt("product_id"));
                System.out.printf("%-5d %-20s %-10d %-10d %-15s%n",
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("selling_price"),
                        rs.getInt("stock"),
                        rs.getString("supplier_name"));
            }
            int id;
            while(true) {
                try {
                    System.out.println("Enter product Id : ");
                    id = scan.nextInt();
                    scan.nextLine();
                    if(!ids.contains(id)){
                        System.out.println("Invalid ID!, please choose from listed products...");
                        continue;
                    }
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Invalid input ,please enter a number...!");
                    scan.nextLine();
                }
            }
            int quantity;
            while(true) {
                try {
                    System.out.print("Enter quantity : ");
                     quantity = scan.nextInt();
                    scan.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Invalid input ,please enter a number...!");
                    scan.nextLine();
                }
            }
            String stockQuery = "SELECT stock FROM products WHERE product_id = ?";
            PreparedStatement stockStmt = con.prepareStatement(stockQuery);
            stockStmt.setInt(1, id);
            ResultSet stockRs = stockStmt.executeQuery();
            if(stockRs.next()){
                int stock = stockRs.getInt("stock");
                if(stock > quantity) {
                    System.out.println("do u wanna continue? yes/no");
                    if (scan.nextLine().equalsIgnoreCase("yes")) {
                        Billing(con, id, quantity,cusId);
                        break;
                    }
                }  else{
                    System.out.println("Stock not available");
                    break;
                }
            }
        }
    }

    private void Billing(Connection con,int id , int quantity,int cusId) throws SQLException {
        String customerName ;
        String sql = "update products " +
                "set stock = stock- ?" +
                " where product_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1,quantity);
        stmt.setInt(2,id);
        stmt.executeUpdate();
        stmt.close();

        stmt=con.prepareStatement("select product_name ,selling_price from products where product_id = ?");
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int amount =(quantity * rs.getInt("Selling_price"));
        customerName = new CustomerDAO().getCustomer(cusId);
        Orders order = new Orders(amount,rs.getString("product_name"),customerName);
        OrdersDAO ordersDAO = new OrdersDAO(order);
        System.out.println("Purchase Completed ,"+customerName+"bought   "+quantity+" "+rs.getString("Product_name") );
        System.out.println("total amount : "+ amount);

    }

}

package DAO;

import Database.Db;
import model.Products;
import model.Supplier;

import java.sql.*;
import java.util.Scanner;

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

//            String sql= "insert into products (product_name,selling_price,supplier_id)values('"
//                    +product.getName()+"'," + product.getPrice()+", (select supplier_id from suppliers where supplier_name ='"+supplier.getName() +
//                    "' and phone= '" +supplier.getPhone()+"'))";
//            Statement stmt = con.createStatement();
//            stmt.executeUpdate(sql);


        }catch (SQLException e){
            System.out.println(" bruh... error while adding products  !! : "+ e);
        }finally{
            db.closeCon();

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
        db.closeCon();

    }

    public void searchProduct() throws SQLException {
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

            }


            System.out.println("Matching products with name " + s);

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
            rs.close();
            stmt.close();
            db.closeCon();
            break;
        }
    }
}

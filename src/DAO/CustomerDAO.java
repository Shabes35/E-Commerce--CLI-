package DAO;

import Database.Db;
import model.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public boolean checkCustomer(String name) throws SQLException {
        Db db = new Db();
        Connection con = db.getConnection();
        String sql = "select * from customers where customer_name = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,name);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public void signUp(Customers customer) {
        Db db = new Db();
        try {
            Connection con = db.getConnection();
            String sql = "insert into customers(customer_name,password) values (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPassword());
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Bruh!! you got an error in signup page..."+ e);
        }
    }

    public int login(Customers customers) {
        Db db = new Db();
        try{
            String sql = "select  * from customers where customer_name= ? and password = ?";
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,customers.getName());
            stmt.setString(2, customers.getPassword());
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
               return 0;
            }
            return rs.getInt("customer_id");

        }
        catch (Exception e){
            System.out.println("Bruh error in login "+ e);
        }
        return 0;

    }

    public String getCustomer(int cusId) {
        Db db = new Db();
        try{
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from customers where customer_id = ?");
            stmt.setInt(1,cusId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {   // ✅ Move to first row
                return rs.getString("customer_name");
            } else {
                System.out.println("⚠️ No customer found with ID: " + cusId);
            }
            rs.close();
            stmt.close();
            con.close();

        }
        catch (Exception e){
            System.out.println("Customer doesn't exist !!!!"+e);
        }

        return "";
    }
}

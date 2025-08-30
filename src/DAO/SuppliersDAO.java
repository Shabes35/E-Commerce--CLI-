package DAO;

import Database.Db;
import model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuppliersDAO {
    Db db = new Db();
    public SuppliersDAO(){

    }

    public SuppliersDAO(Supplier supplier) {

        try {
            Connection con = db.getConnection();
            SuppliersDAO check = new SuppliersDAO();

            if(!check.supplierExist(supplier.getName(),supplier.getPhone())) {
                String sql = "insert into suppliers (supplier_name,phone)values('"
                        + supplier.getName() + "','" + supplier.getPhone() + "')";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
            }

        }catch (SQLException e){
            System.out.println(" bruh error in supplier  !! : "+ e);
        }
    }

    public boolean supplierExist(String name, String phone) throws SQLException {

        Connection con = db.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs =stmt.executeQuery("Select * from suppliers where supplier_name ='"+name+"'and phone='"+phone+"'");
        return rs.next();

    }

    public void viewSupplier() throws SQLException {
        Db db = new Db();
        Connection con= db.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from suppliers ");
        while (rs.next()) {
            System.out.printf("ID: %-3d | Supplier Name: %-20s | Phone: %-15s%n",
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("phone"));
        }


    }
}

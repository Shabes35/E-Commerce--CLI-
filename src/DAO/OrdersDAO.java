package DAO;

import Database.Db;
import model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrdersDAO {
    public OrdersDAO(Orders order) {
        Db db = new Db();
        try{
            String sql = "insert into orders (order_date,product_name,total_amount,customer_name) values" +
                    " (?,?,?,?)";
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1,order.getDate());
            stmt.setString(2,order.getProName());
            stmt.setInt(3,order.getAmount());
            stmt.setString(4, order.getCusName());
            stmt.executeUpdate();
            stmt.close();
            con.close();
        }catch (Exception e){
            System.out.println("Bruh error in placing orders...."+e);
        }

    }
}

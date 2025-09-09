package model;

import java.sql.Date;
import java.time.LocalDate;

public class Orders {
    private LocalDate date ;
    private int amount ;
    private String cusName;
    private String proName;

    public Orders(int amount, String proName, String cusName) {
        this.cusName = cusName;
        date =LocalDate.now();
        this.amount = amount;
        this.proName = proName;
    }

    public String getProName() {
        return proName;
    }



    public String getCusName() {
        return cusName;
    }



    public Date getDate() {
        return Date.valueOf(date);
    }


    public int getAmount() {
        return amount;
    }


}

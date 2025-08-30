package model;


public class Supplier {
    private int supplier_id;
    private String name ;
    private String phone;

    public Supplier(){

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Supplier(String name, String phone) {


            this.name = name;
            this.phone = phone;


    }
}

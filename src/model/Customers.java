package model;

public class Customers {
    private String name;
    private String password;
    public Customers(String name,String password) {
        this.name=name;
        this.password=password;
    }

    public Customers() {

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

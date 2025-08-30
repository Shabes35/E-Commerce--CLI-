package model;

public class Products {


    private String name;
    private int price;
    private int supplier_id;
    private int stock;
    public Products(){

    }

    public Products( String name, int price,int stock) {

        this.name = name;
        this.price = price;
        this.stock = stock;

    }
    public  int getStock(){
        return stock;
    }


    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public void setStock(int stock) {
        this.stock = stock;
    }
}

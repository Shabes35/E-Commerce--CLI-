
# 🛒 JDBC E-Commerce Store

A simple **console-based e-commerce management system** built using **Java + JDBC + MySQL**.  
This project demonstrates how to connect Java applications with a MySQL database using JDBC, and manage products, suppliers, customers, and orders.

---

## 🚀 Features
- Add new **products** with stock and supplier details.
- Add and view **suppliers**.
- Customer **signup & login** functionality.
- Place **orders** and auto-update stock.
- View available products with supplier info.
- Clean structure with `DAO`, `model`, and `Database` layers.

---

## 📂 Project Structure
```

src/
├── DAO/                # Data Access Objects (database operations)
│    ├── CustomerDAO.java
│    ├── OrdersDAO.java
│    ├── ProductDAO.java
│    └── SuppliersDAO.java
├── Database/
│    └── Db.java         # Handles DB connection
├── model/              # Entity classes
│    ├── Customers.java
│    ├── Orders.java
│    ├── Products.java
│    └── Supplier.java
└── Main.java           # Entry point (console-based app)

````

---

## 🗄️ Database Setup
1. Install **MySQL** and create a database:
   ```sql
   CREATE DATABASE store;
   USE store;
   ````

2. Create required tables:

   ```sql
   CREATE TABLE customers (
       customer_id INT AUTO_INCREMENT PRIMARY KEY,
       customer_name VARCHAR(100) UNIQUE,
       password VARCHAR(100)
   );

   CREATE TABLE suppliers (
       supplier_id INT AUTO_INCREMENT PRIMARY KEY,
       supplier_name VARCHAR(100),
       phone VARCHAR(20)
   );

   CREATE TABLE products (
       product_id INT AUTO_INCREMENT PRIMARY KEY,
       product_name VARCHAR(100),
       selling_price INT,
       stock INT,
       supplier_id INT,
       FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE SET NULL
   );

   CREATE TABLE orders (
       order_id INT AUTO_INCREMENT PRIMARY KEY,
       order_date DATE,
       product_name VARCHAR(100),
       total_amount INT,
       customer_name VARCHAR(100)
   );
    ```

3. Update your DB credentials in `Database/Db.java`:

   ```java
   private final static String url="jdbc:mysql://localhost:3306/store";
   private final static String user="your-username";
   private final static String password="your-password";
   ```


1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/jdbc-ecommerce-store.git
   cd jdbc-ecommerce-store
   ```

2. Compile and run the project:

   ```bash
   javac -d out src/**/*.java
   java -cp out Main
   ```

---

## ✨ Future Improvements

* Add a **GUI / Web interface** (Swing, JavaFX, or Spring Boot + Thymeleaf/React).
* Implement **admin panel** for managing stock and suppliers.
* Add proper **exception handling & validation**.
* Enhance order management with detailed invoices.

---

## 📜 License

This project is for **educational purposes only**. Feel free to use and modify.

```


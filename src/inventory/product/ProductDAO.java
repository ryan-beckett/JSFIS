
package inventory.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a data access object for retrieving and persisting
 * product data to a database. Each database transaction requires retrieving a
 * new connection to the database. As a best pratice a connection pool should be
 * used.
 */

public class ProductDAO implements Serializable
{
     private static final long serialVersionUID = 1L;
     private final String driver = "com.mysql.jdbc.Driver";
     private final String url = "jdbc:mysql://localhost:3306/inventory_db";
     private final String user = "inventory_user";
     private final String pass = "inventory_pass";

     public Connection getConnection()
     {
          try {
               Class.forName(driver).newInstance();
               Connection conn = DriverManager.getConnection(url, user, pass);
               conn.setAutoCommit(false);
               return conn;
          }
          catch (Exception e) {
               log("Connection to database failed: " + e.toString());
          }
          return null;
     }

     public void closeConnection(Connection conn)
     {
          try {
               conn.close();
          }
          catch (SQLException e) {
               log("An error occurred while closing the database connection: "
                         + e.toString());
          }
     }

     public List<Product> listAll()
     {
          ArrayList<Product> products = new ArrayList<Product>();
          String sql = "SELECT * FROM product";
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    Statement stmt = conn.createStatement(
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                         Product p = new Product(rs.getInt(1), rs.getString(2),
                                   rs.getString(3), rs.getInt(4) + "");
                         products.add(p);
                    }
                    stmt.close();
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
               }
               closeConnection(conn);
          }
          return products;
     }

     public Product listById(int id)
     {
          Product p = null;
          String sql = "SELECT * FROM product WHERE id = " + id;
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    Statement stmt = conn.createStatement(
                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                              ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                         p = new Product(rs.getInt(1), rs.getString(2),
                                   rs.getString(3), rs.getInt(4) + "");
                    }
                    stmt.close();
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
               }
               closeConnection(conn);
          }
          return p;
     }

     public List<Product> listByName(String name)
     {
          ArrayList<Product> products = new ArrayList<Product>();
          String sql = "SELECT * FROM product WHERE LCASE(name) LIKE ?";
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, "%" + name.toLowerCase() + "%");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                         Product p = new Product(rs.getInt(1), rs.getString(2),
                                   rs.getString(3), rs.getInt(4) + "");
                         products.add(p);
                    }
                    stmt.close();
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
               }
               closeConnection(conn);
          }
          return products;
     }

     public boolean create(Product p)
     {
          String sql = "INSERT INTO product (name, description, quantity) VALUES (?, ?, ?)";
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, p.getName());
                    stmt.setString(2, p.getDescription());
                    stmt.setInt(3, Integer.parseInt(p.getQuantity()));
                    stmt.executeUpdate();
                    conn.commit();
                    stmt.close();
                    closeConnection(conn);
                    return true;
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
                    rollback(conn);
                    closeConnection(conn);
               }
          }
          return false;
     }

     public boolean update(Product p)
     {
          String sql = "UPDATE product SET id = ?, name = ?, description = ?, quantity = ? WHERE id = ?";
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, p.getId());
                    stmt.setString(2, p.getName());
                    stmt.setString(3, p.getDescription());
                    stmt.setInt(4, Integer.parseInt(p.getQuantity()));
                    stmt.setInt(5, p.getId());
                    stmt.executeUpdate();
                    conn.commit();
                    stmt.close();
                    closeConnection(conn);
                    return true;
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
                    rollback(conn);
                    closeConnection(conn);
               }
          }
          return false;
     }

     public boolean delete(Product p)
     {
          String sql = "DELETE FROM product WHERE id = ?";
          Connection conn = getConnection();
          if (conn != null) {
               try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, p.getId());
                    stmt.executeUpdate();
                    conn.commit();
                    stmt.close();
                    closeConnection(conn);
                    return true;
               }
               catch (SQLException e) {
                    log("A SQL error occurred: " + e.toString());
                    rollback(conn);
                    closeConnection(conn);
               }
          }
          return false;
     }

     private void rollback(Connection conn)
     {
          if (conn != null) {
               try {
                    conn.rollback();
                    log("Database transaction rollback was successful.");
                    return;
               }
               catch (SQLException e) {
                    log("Database transaction rollback failed: " + e.toString());
               }
          }
     }

     private void log(String msg)
     {
          Logger.getLogger("ProductDAO").log(Level.INFO, msg);
     }
}

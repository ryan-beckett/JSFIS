
package inventory.product;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * The main controller for the inventory  system. This is a session-scoped,
 * managed bean that serves as an intermediary between the presentation and
 * persistance layers. We bind the UI components to variables defined in this
 * controller and the JSF framework takes care of the rest. Similarly, we hide
 * the intracacies of database access behind a data access object to retrieve
 * and persist data using a simple API.
 */
@ManagedBean(name = "controller")
@SessionScoped
public class ProductController implements Serializable
{
     private static final long serialVersionUID = 1L;
     private ProductDAO dao;
     private List<Product> products;
     private Product product;
     private String searchTerm;
     private String searchField;
     private boolean edit;
     private String action;
     private String status;

     @PostConstruct
     public void init()
     {
          product = new Product();
          action = "Add";
          dao = new ProductDAO();
          products = dao.listAll();
     }

     public void add()
     {
          status = "";
          if (edit) {
               save();
               return;
          }
          if (isValidForm()) {
               if (dao.create(product)) {
                    products = dao.listAll();
                    status = "The product was added.";
               }
               else {
                    status = "An error occurred.";
               }
               product = new Product();
          }
     }

     public void edit(Product product)
     {
          status = "";
          this.product = product;
          edit = true;
          action = "Update";
     }

     public void delete(Product product)
     {
          status = "";
          if (dao.delete(product)) {
               products.remove(product);
               status = "The product was deleted.";
          }
          else {
               status = "An error occurred.";
          }
          product = new Product();
     }

     public void save()
     {
          status = "";
          if (isValidForm()) {
               if (dao.update(product)) {
                    Product old = getProductById(product.getId());
                    int index = products.indexOf(old);
                    products.remove(index);
                    products.add(index, product);
                    status = "The product was updated.";
               }
               else {
                    status = "An error occurred.";
               }
               product = new Product();
               edit = false;
               action = "Add";
          }
          products = dao.listAll();
     }

     public void cancel()
     {
          status = "";
          product = new Product();
          edit = false;
          action = "Add";
     }

     public void search()
     {
          status = "";
          if (searchField.equals("id")) {
               if (isPositiveInteger(searchTerm)) {
                    products.clear();
                    Product target = dao.listById(Integer.parseInt(searchTerm));
                    if (target != null)
                         products.add(target);
               }
               else {
                    status = "Please enter a valid id.";
               }
          }
          else if (searchField.equals("name")) {
               if (isValidField(searchTerm)) {
                    products.clear();
                    List<Product> matches = dao.listByName(searchTerm);
                    if (!matches.isEmpty())
                         products.addAll(matches);
               }
               else {
                    status = "Please enter a valid name.";
               }
          }
          product = new Product();
     }

     public void viewAll()
     {
          status = "";
          products = dao.listAll();
          searchTerm = "";
          product = new Product();
     }

     private boolean isValidForm()
     {
          if (!isValidField(product.getName())) {
               status = "Enter a valid product name.";
               return false;
          }
          if (!isValidField(product.getDescription())) {
               status = "Enter a valid product description.";
               return false;
          }
          if (!isPositiveInteger(product.getQuantity())) {
               status = "Enter a valid, postive quantity.";
               return false;
          }
          return true;
     }

     private boolean isValidField(String fld)
     {
          if (fld == null || fld.length() == 0)
               return false;
          return true;
     }

     private boolean isPositiveInteger(String str)
     {
          if (isValidField(str)) {
               try {
                    return Integer.parseInt(str) >= 0;
               }
               catch (NumberFormatException e) {
               }
          }
          return false;
     }

     private Product getProductById(int id)
     {
          for (Product p : products) {
               if (p.getId() == id)
                    return p;
          }
          return null;
     }

     public List<Product> getProducts()
     {
          return products;
     }

     public void setProducts(List<Product> products)
     {
          this.products = products;
     }

     public Product getProduct()
     {
          return product;
     }

     public void setProduct(Product product)
     {
          this.product = product;
     }

     public String getSearchTerm()
     {
          return searchTerm;
     }

     public void setSearchTerm(String searchTerm)
     {
          this.searchTerm = searchTerm;
     }

     public String getSearchField()
     {
          return searchField;
     }

     public void setSearchField(String searchField)
     {
          this.searchField = searchField;
     }

     public boolean isEdit()
     {
          return edit;
     }

     public String getStatus()
     {
          return status;
     }

     public void setStatus(String status)
     {
          this.status = status;
     }

     public String getAction()
     {
          return action;
     }

     public void setAction(String action)
     {
          this.action = action;
     }

}

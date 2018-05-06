
package inventory.product;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

/**
 * This class is the product domain model object.
 */
@ManagedBean
public class Product implements Serializable
{
     private static final long serialVersionUID = 1L;
     private Integer id;
     private String name;
     private String description;
     private String quantity;

     public Product()
     {
     }

     public Product(Integer id, String name, String description, String quantity)
     {
          super();
          this.id = id;
          this.name = name;
          this.description = description;
          this.quantity = quantity;
     }

     @Override
     public int hashCode()
     {
          final int prime = 31;
          int result = 1;
          result = prime * result
                    + ((description == null) ? 0 : description.hashCode());
          result = prime * result + ((id == null) ? 0 : id.hashCode());
          result = prime * result + ((name == null) ? 0 : name.hashCode());
          result = prime * result
                    + ((quantity == null) ? 0 : quantity.hashCode());
          return result;
     }

     @Override
     public boolean equals(Object obj)
     {
          if (this == obj)
               return true;
          if (obj == null)
               return false;
          if (!(obj instanceof Product))
               return false;
          Product other = (Product) obj;
          if (description == null) {
               if (other.description != null)
                    return false;
          }
          else if (!description.equals(other.description))
               return false;
          if (id == null) {
               if (other.id != null)
                    return false;
          }
          else if (!id.equals(other.id))
               return false;
          if (name == null) {
               if (other.name != null)
                    return false;
          }
          else if (!name.equals(other.name))
               return false;
          if (quantity == null) {
               if (other.quantity != null)
                    return false;
          }
          else if (!quantity.equals(other.quantity))
               return false;
          return true;
     }

     public Integer getId()
     {
          return id;
     }

     public void setId(Integer id)
     {
          this.id = id;
     }

     public void setQuantity(String quantity)
     {
          this.quantity = quantity;
     }

     public String getName()
     {
          return name;
     }

     public void setName(String name)
     {
          this.name = name;
     }

     public String getDescription()
     {
          return description;
     }

     public void setDescription(String description)
     {
          this.description = description;
     }

     public String getQuantity()
     {
          return quantity;
     }

}

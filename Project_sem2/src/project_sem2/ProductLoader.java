
package Project_Sem2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader {
    public static List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = Product.deserialize(line);
                products.add(product);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return products;
    }

    public static void main(String[] args) {
        List<Product> productList = loadProducts("ProductList.txt");

        // Perform operations using the loaded products
        for (Product product : productList) {
            System.out.println(product.getName() + " - $" + product.getPrice());
            // Perform any other required operations with the product
        }
    }
}
 

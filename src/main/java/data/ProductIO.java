package data;

import business.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductIO {
    private static final List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("8601", "86 (the band) - True Life Songs and Pictures", 14.95));
        products.add(new Product("pf01", "Paddlefoot - The first CD", 12.95));
        products.add(new Product("pf02", "Paddlefoot - The second CD", 14.95));
        products.add(new Product("jr01", "Joe Rut - Genuine Wood Grained Finish", 14.95));
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static Product getProduct(String productCode) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(productCode)) {
                return p;
            }
        }
        return null;
    }
}
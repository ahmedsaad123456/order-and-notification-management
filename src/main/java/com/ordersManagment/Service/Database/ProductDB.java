package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Setter

/**
 * ProductDB class
 *
 * used to store products data and the number of products in each category
 *
 */

public class ProductDB extends Database {

    @Getter
    private static ArrayList<Product> products;

    @Getter
    private static HashMap<String, Integer> category;

    //------------------------------------------------------------------------------------------------------------

    static {
        products = new ArrayList<>();
        category = new HashMap<>();
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * save product in database
     *
     * @param p product that we want to store it
     */

    public static void saveProduct(Product p) {
        p.setAmount(1);
        products.add(p);
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * update product amount
     *
     * @param p         product that we want to update the amount of it
     * @param newAmount is the updated amount of the product
     */

    public static void updateProductAmount(Product p, int newAmount) {
        for (Product product : products) {
            if (product.getSerialNumber().equals(p.getSerialNumber())) {
                product.setAmount(newAmount);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * update category
     *
     * @param p product that we get from it the category name that will be updated
     */

    public static void updateCategory(Product p) {
        if (category.containsKey(p.getCategory())) {

            int count = category.get(p.getCategory());
            category.put(p.getCategory(), count + 1);
        } else {
            category.put(p.getCategory(), 1);
        }
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get product amount by serial number
     *
     * @param s serial number of the product
     * @return the amount of the product if exist and zero if not exist
     */
    public static int getProductAmountBySerialNumber(String s) {
        for (Product product : products) {
            if (product.getSerialNumber().equals(s)) {
                return product.getAmount();
            }
        }

        return 0;

    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get product by serial number
     *
     * @param serialNumber of the product
     * @return the product if exist and null if not exist
     */

    public static Product getProductBySerialNumber(String serialNumber) {
        for (Product product : products) {
            if (product.getSerialNumber().equals(serialNumber)) {
                return product;
            }
        }

        return null;
    }

    public static void reduceProductAmount(String serialNumber, int amount) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getSerialNumber().equals(serialNumber)) {
                category.put(products.get(i).getCategory(), category.get(products.get(i).getCategory()) - amount);
                products.get(i).setAmount(products.get(i).getAmount() - amount);
                break;
            }
        }
    }

    public static void increaseProductAmount(String serialNumber, int amount) {
        reduceProductAmount(serialNumber, -amount);
    }
}

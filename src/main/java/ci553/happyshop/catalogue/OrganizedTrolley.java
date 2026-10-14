package ci553.happyshop.catalogue;

public class OrganizedTrolley {
    @Override
    public void addProduct(Product product) {
        Product existing = findById(product.getProductId());

        if (existing != null) {
            // When the same product is already in trolley it will just add the quantities together
            existing.setOrderedQuantity(
                    existing.getOrderedQuantity() + product.getOrderedQuantity());
        } else {
            // For new products, it stores a COPY, never the caller's object
            getProducts().add(copyOf(product));
        }
        Collections.sort(getProducts()); // Product implements Comparable (by ID)
    }

    // here, it returns the product in the trolley with this ID, or null if none
    private Product findById(String productId) {
        for (Product p : getProducts()) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    // Here, it just makes a copy so later merges never change the original object
    private Product copyOf(Product p) {
        return new Product(p.getProductId(), p.getProductDescription(),
                p.getProductImageName(), p.getUnitPrice(),
                p.getOrderedQuantity(), p.getStockQuantity());
    }
}
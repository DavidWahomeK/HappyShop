package ci553.happyshop.catalogue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizedTrolleyTest {

    private Product makeProduct(String id, double price) {
        return new Product(id, "item " + id, id + ".jpg", price, 100);
    }

    @Test
   public void sameProductAddedTwiceIsMerged() {
        Trolley trolley = new OrganizedTrolley();
        Product p = makeProduct("0001", 10.0);
        trolley.addProduct(p);
        trolley.addProduct(p);
        assertEquals(1, trolley.getProducts().size());
        assertEquals(2, trolley.getProducts().get(0).getOrderedQuantity());
    }

    @Test
   public void sameObjectAddedThreeTimesGivesQuantityThree() {
        Trolley trolley = new OrganizedTrolley();
        Product p = makeProduct("0001", 10.0);
        trolley.addProduct(p);
        trolley.addProduct(p);
        trolley.addProduct(p);
        assertEquals(3, trolley.getProducts().get(0).getOrderedQuantity());
        assertEquals(1, p.getOrderedQuantity()); // original is untouched
    }

    @Test
   public void getTotalAddsUpPriceTimesQuantity() {
        Trolley trolley = new OrganizedTrolley();
        trolley.addProduct(makeProduct("0001", 10.0));
        trolley.addProduct(makeProduct("0001", 10.0));
        trolley.addProduct(makeProduct("0002", 5.5));
        assertEquals(25.5, trolley.getTotal(), 0.001);
    }

    @Test
    void removeProductRemovesWholeLine() {
        Trolley trolley = new OrganizedTrolley();
        Product p = makeProduct("0001", 10.0);
        trolley.addProduct(p);
        trolley.addProduct(p);
        trolley.addProduct(makeProduct("0002", 5.0));
        assertTrue(trolley.removeProduct("0001"));
        assertEquals(1, trolley.getProducts().size());
        assertEquals("0002", trolley.getProducts().get(0).getProductId());
        assertFalse(trolley.removeProduct("9999"));
    }
}
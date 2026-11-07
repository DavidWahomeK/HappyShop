package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Product;
import ci553.happyshop.storageAccess.DatabaseRW;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

class CustomerModelStockShortageTest {

    @Test
    void insufficientProductIsRemovedAndCustomerIsNotified() {
        DatabaseRW mockDatabase = mock(DatabaseRW.class);
        CustomerModel model = new CustomerModel(mockDatabase);
        model.cusView = mock(CustomerView.class);
        RemoveProductNotifier mockNotifier = mock(RemoveProductNotifier.class);
        model.removeProNotifier = mockNotifier;

        Product shortItem = new Product("0001", "toast", "0001.jpg", 15.19, 5, 2); // 5 ordered, 2 in stock
        Product okItem = new Product("0002", "radio", "0002.jpg", 29.99, 1, 50);
        model.getTrolley().addProduct(shortItem);
        model.getTrolley().addProduct(okItem);

        ArrayList<Product> insufficient = new ArrayList<>();
        insufficient.add(shortItem);
        when(mockDatabase.purchaseStocks(any())).thenReturn(insufficient);

        model.checkOut();

        assertEquals(1, model.getTrolley().getProducts().size());
        assertEquals("0002", model.getTrolley().getProducts().get(0).getProductId());
        verify(mockNotifier).showRemovalMsg(contains("0001"));
    }

    @Test
    void cancelClosesTheNotifierWindow() {
        CustomerModel model = new CustomerModel(mock(DatabaseRW.class));
        model.cusView = mock(CustomerView.class);
        RemoveProductNotifier mockNotifier = mock(RemoveProductNotifier.class);
        model.removeProNotifier = mockNotifier;

        model.cancel();

        verify(mockNotifier).closeNotifierWindow();
    }
}
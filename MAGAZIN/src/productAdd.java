import java.util.ArrayList;
import java.util.List;


public class productAdd {
    private List<ProductX> productList;

    public productAdd() {
        // Inițializarea listei de produse
        productList = new ArrayList<>();
    }

    public boolean addProduct(ProductX product) {
        // Verificarea dacă produsul există deja în listă
        if (productList.contains(product)) {
            return false; // Produsul există deja
        }

        // Adăugarea produsului în listă
        productList.add(product);
        return true; // Adăugarea produsului a fost realizată cu succes
    }

    public List<ProductX> getAllProducts() {
        return productList;
    }

    // Alte metode pentru gestionarea produselor (actualizare, ștergere, căutare, etc.)
}
package pl.kdrozd.examples.service;


import org.springframework.stereotype.Service;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.mapper.ProductMapper;
import pl.kdrozd.examples.model.Product;

@Service
public class ProductService {

    private final ProductMapper mapper;
    private final DummyProductRepository repo;

    public ProductService(ProductMapper mapper) {
        this.mapper = mapper;
        this.repo = new DummyProductRepository();
    }

    public ProductDTO update(long productId, ProductDTO update) {
        Product destination = repo.findById(productId);
        // check if present blah, blah...

        mapper.update(update, destination);
        repo.save(destination);

        return mapper.map(destination);
    }

    // Dummy class
    private static class DummyProductRepository {

        public Product findById(long id) {
            return new Product(id, "whatever", 0, "description", "company");
        }

        public void save(Product product) {
            //do nothing
        }
    }
}
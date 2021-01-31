package pl.kdrozd.examples.service;


import org.springframework.stereotype.Service;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.mapper.ProductMapper;
import pl.kdrozd.examples.model.Product;
import pl.kdrozd.examples.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repo;

    public ProductService(ProductMapper mapper, ProductRepository repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    public ProductDTO update(long productId, ProductDTO update) {
        Product product = repo.findById(productId).orElseThrow(() -> {
            throw new RuntimeException("Product with id <" + productId + "> not found.");
        });

        mapper.update(update, product);
        repo.save(product);

        return mapper.map(product);
    }

}
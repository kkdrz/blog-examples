package pl.kdrozd.examples.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kdrozd.examples.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
